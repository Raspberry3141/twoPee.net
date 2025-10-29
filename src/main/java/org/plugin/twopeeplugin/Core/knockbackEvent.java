package org.plugin.twopeeplugin.Core;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import org.plugin.twopeeplugin.TwoPeePlugin;

public class knockbackEvent implements Listener {
    final double knockbackHorizontal = 0.4D;
    final double knockbackVertical = 0.4D;
    final double knockbackVerticalLimit = 0.4D;
    final double knockbackExtraHorizontal = 0.5D;
    final double knockbackExtraVertical = 0.1D;
    private pracManager pracmanager;

    public knockbackEvent(pracManager p) {
        pracmanager =p;
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player attacker = (Player) event.getDamager();
        if (pracmanager.isInPrac(attacker)) return;
        Entity victim = event.getEntity();
        switch (attacker.getItemInHand().getType()) {
            case BLAZE_ROD:
                cancelKb(event,attacker,victim);
                dealKnockback(victim,leftTransformKb(calcualteKnockback(victim,attacker)));
                break;
            case BONE:
                cancelKb(event,attacker,victim);
                dealKnockback(victim,rightTransformKb(calcualteKnockback(victim,attacker)));
                break;
            case WOOD_HOE:
                cancelKb(event,attacker,victim);
                dealKnockback(victim,invertKb(calcualteKnockback(victim,attacker)));
                break;
        }
    }

    private void cancelKb(EntityDamageEvent event, Player attacker, Entity victim) {
        double damageAmount = event.getDamage();
        event.setCancelled(true);
        if (victim instanceof  Player) ((Player) victim).damage(damageAmount, attacker);
        else {
            ((LivingEntity) victim).damage(damageAmount);
        }
    }

    private Vector invertKb(Vector vector) {
        vector.setZ(-1 * vector.getZ());
        vector.setX(-1 * vector.getX());
        return vector;
    }

    private Vector leftTransformKb(Vector vector) {
        //(z,x)=>(-x,z)
        double transformedX = vector.getZ();
        vector.setZ(-1 * vector.getX());
        vector.setX(transformedX);
        return vector;
    }

    private Vector rightTransformKb(Vector vector) {
        //(z,x)=>(x,-z)
        double transformedX = -1 * vector.getZ();
        vector.setZ(vector.getX());
        vector.setX(transformedX);
        return vector;
    }

    private void dealKnockback(Entity victim,Vector knockback) {
        Bukkit.getScheduler().runTaskLater(TwoPeePlugin.getInstance(), () -> {
            victim.setVelocity(knockback);
        }, 1L);
    }

    private Vector calcualteKnockback(Entity victim, Player attacker) {
        //NOTE: This likely is not the equivalent of Vanilla Kb calculation
        double d0 = attacker.getLocation().getX() - victim.getLocation().getX();
        double d1;
        for (d1 = attacker.getLocation().getZ() - victim.getLocation().getZ();
             d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
            d0 = (Math.random() - Math.random()) * 0.01D;
        double magnitude = Math.sqrt(d0 * d0 + d1 * d1);
        Vector playerVelocity = victim.getVelocity();
        playerVelocity.setX((playerVelocity.getX() / 2) - (d0 / magnitude * knockbackHorizontal));
        playerVelocity.setY((playerVelocity.getY() / 2) + knockbackVertical);
        playerVelocity.setZ((playerVelocity.getZ() / 2) - (d1 / magnitude * knockbackHorizontal));
        int i = attacker.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK);
        if (attacker.isSprinting()) ++i;
        if (playerVelocity.getY() > knockbackVerticalLimit)
            playerVelocity.setY(knockbackVerticalLimit);
        if (i > 0)
            playerVelocity.add(new Vector((-Math.sin(attacker.getLocation().getYaw() * 3.1415927F / 180.0F) *
                    (float) i * knockbackExtraHorizontal), knockbackExtraVertical,
                    Math.cos(attacker.getLocation().getYaw() * 3.1415927F / 180.0F) *
                            (float) i * knockbackExtraHorizontal));
        return playerVelocity;
    }
}
