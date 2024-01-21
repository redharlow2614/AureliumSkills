package dev.aurelium.auraskills.common.source;

import dev.aurelium.auraskills.api.registry.NamespacedId;
import dev.aurelium.auraskills.api.source.SourceType;
import dev.aurelium.auraskills.api.source.SourceValues;
import dev.aurelium.auraskills.api.source.XpSource;
import dev.aurelium.auraskills.common.AuraSkillsPlugin;
import dev.aurelium.auraskills.common.message.MessageKey;
import dev.aurelium.auraskills.common.util.text.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class Source implements XpSource {

    private final AuraSkillsPlugin plugin;
    private final SourceValues values;

    public Source(AuraSkillsPlugin plugin, SourceValues values) {
        this.plugin = plugin;
        this.values = values;
    }

    @Override
    public NamespacedId getId() {
        return values.getId();
    }

    @Override
    public SourceType getType() {
        return values.getType();
    }

    @Override
    public String getDisplayName(Locale locale) {
        SourceType sourceType = getType();
        if (sourceType == null) {
            return getId().getKey();
        }
        String messagePath = "sources." + sourceType.toString().toLowerCase(Locale.ROOT) + "." + getId().getKey().toLowerCase(Locale.ROOT);
        String msg = plugin.getMsg(MessageKey.of(messagePath), locale);
        if (msg.equals(messagePath)) {
            // Try to use defined display name
            if (values.getDisplayName() != null) {
                return values.getDisplayName();
            }
        }
        return msg; // Return if exists in messages
    }

    @Override
    public @Nullable String getUnitName(Locale locale) {
        String unitName = plugin.getItemRegistry().getSourceMenuItems().getSourceUnit(this);
        if (unitName == null) {
            return null;
        }
        // Try to replace placeholders
        for (String keyStr : TextUtil.getPlaceholders(unitName)) {
            MessageKey key = MessageKey.of(keyStr);
            String message = plugin.getMsg(key, locale);
            unitName = TextUtil.replace(unitName, "{" + keyStr + "}", message);
        }
        return unitName;
    }

    @Override
    public String name() {
        return getId().getKey().toUpperCase(Locale.ROOT);
    }

    @Override
    public double getXp() {
        return values.getXp();
    }

    @Override
    public String toString() {
        return getId().toString();
    }

}
