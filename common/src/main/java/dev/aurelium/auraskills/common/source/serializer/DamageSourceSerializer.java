package dev.aurelium.auraskills.common.source.serializer;

import dev.aurelium.auraskills.api.source.SourceType;
import dev.aurelium.auraskills.api.source.type.DamageXpSource;
import dev.aurelium.auraskills.common.AuraSkillsPlugin;
import dev.aurelium.auraskills.common.source.type.DamageSource;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;

public class DamageSourceSerializer extends SourceSerializer<DamageSource> {

    public DamageSourceSerializer(AuraSkillsPlugin plugin, SourceType sourceType, String sourceName) {
        super(plugin, sourceType, sourceName);
    }

    @Override
    public DamageSource deserialize(Type type, ConfigurationNode source) throws SerializationException {
        DamageXpSource.DamageCause[] causes = pluralizedArray("cause", source, DamageXpSource.DamageCause.class);
        DamageXpSource.DamageCause[] excludedCauses = pluralizedArray("excluded_cause", source, DamageXpSource.DamageCause.class);
        String damager = source.node("damager").getString();
        boolean mustSurvive = source.node("must_survive").getBoolean(true);
        boolean useOriginalDamage = source.node("use_original_damage").getBoolean(true);

        return new DamageSource(plugin, parseValues(source), causes, excludedCauses, damager, mustSurvive, useOriginalDamage);
    }
}
