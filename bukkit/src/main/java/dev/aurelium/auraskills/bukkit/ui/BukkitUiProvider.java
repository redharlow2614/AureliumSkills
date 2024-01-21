package dev.aurelium.auraskills.bukkit.ui;

import dev.aurelium.auraskills.api.skill.Skill;
import dev.aurelium.auraskills.bukkit.AuraSkills;
import dev.aurelium.auraskills.bukkit.hooks.ProtocolLibHook;
import dev.aurelium.auraskills.bukkit.user.BukkitUser;
import dev.aurelium.auraskills.common.ui.ActionBarManager;
import dev.aurelium.auraskills.common.ui.UiProvider;
import dev.aurelium.auraskills.common.user.User;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class BukkitUiProvider implements UiProvider {

    private final AuraSkills plugin;
    private final ActionBarManager actionBarManager;
    private final BossBarManager bossBarManager;

    public BukkitUiProvider(AuraSkills plugin) {
        this.plugin = plugin;
        this.actionBarManager = new BukkitActionBarManager(plugin, this);
        this.bossBarManager = new BossBarManager(plugin);
        plugin.getServer().getPluginManager().registerEvents(bossBarManager, plugin);
    }

    @Override
    public ActionBarManager getActionBarManager() {
        return actionBarManager;
    }

    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void sendActionBar(User user, String message) {
        Player player = ((BukkitUser) user).getPlayer();
        if (player == null) return;

        if (plugin.getHookManager().isRegistered(ProtocolLibHook.class)) {
            ProtocolLibHook hook = plugin.getHookManager().getHook(ProtocolLibHook.class);
            hook.sendActionBar(player, message);
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }

    @Override
    public void sendXpBossBar(User user, Skill skill, double currentXp, double levelXp, double xpGained, int level, boolean maxed) {
        Player player = ((BukkitUser) user).getPlayer();
        if (player == null) return;
        bossBarManager.sendBossBar(player, skill, currentXp, levelXp, xpGained, level, maxed);
    }

    @Override
    public void sendTitle(User user, String title, String subtitle, int fadeIn, int stay, int fadeOut) {

    }
}
