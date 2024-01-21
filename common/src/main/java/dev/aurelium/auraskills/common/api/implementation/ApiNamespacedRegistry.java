package dev.aurelium.auraskills.common.api.implementation;

import dev.aurelium.auraskills.api.ability.CustomAbility;
import dev.aurelium.auraskills.api.mana.CustomManaAbility;
import dev.aurelium.auraskills.api.registry.NamespaceIdentified;
import dev.aurelium.auraskills.api.registry.NamespacedId;
import dev.aurelium.auraskills.api.registry.NamespacedRegistry;
import dev.aurelium.auraskills.api.skill.CustomSkill;
import dev.aurelium.auraskills.api.source.SourceType;
import dev.aurelium.auraskills.api.source.XpSource;
import dev.aurelium.auraskills.api.source.XpSourceSerializer;
import dev.aurelium.auraskills.api.stat.CustomStat;
import dev.aurelium.auraskills.api.trait.CustomTrait;
import dev.aurelium.auraskills.common.AuraSkillsPlugin;

import java.io.File;

public class ApiNamespacedRegistry implements NamespacedRegistry {

    private final String namespace;
    private final AuraSkillsPlugin plugin;
    private File contentDirectory;

    public ApiNamespacedRegistry(AuraSkillsPlugin plugin, String namespace, File contentDirectory) {
        this.plugin = plugin;
        this.namespace = namespace;
        this.contentDirectory = contentDirectory;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public void registerSkill(CustomSkill skill) {
        validateNamespace(skill);
        plugin.getSkillRegistry().register(skill.getId(), skill, plugin.getSkillManager().getSupplier());
    }

    @Override
    public void registerAbility(CustomAbility ability) {
        validateNamespace(ability);
        plugin.getAbilityRegistry().register(ability.getId(), ability, plugin.getAbilityManager().getSupplier());
    }

    @Override
    public void registerManaAbility(CustomManaAbility manaAbility) {
        validateNamespace(manaAbility);
        plugin.getManaAbilityRegistry().register(manaAbility.getId(), manaAbility, plugin.getManaAbilityManager().getSupplier());
    }

    @Override
    public void registerStat(CustomStat stat) {
        validateNamespace(stat);
        plugin.getStatRegistry().register(stat.getId(), stat, plugin.getStatManager().getSupplier());
    }

    @Override
    public void registerTrait(CustomTrait trait) {
        validateNamespace(trait);
        plugin.getTraitRegistry().register(trait.getId(), trait, plugin.getTraitManager().getSupplier());
    }

    @Override
    public void registerSourceType(String name, Class<? extends XpSource> sourceClass, Class<? extends XpSourceSerializer<?>> serializerClass) {
        NamespacedId id = NamespacedId.of(namespace, name);
        SourceType sourceType = new ApiSourceType(id, sourceClass, serializerClass);
        plugin.getSourceTypeRegistry().register(id, sourceType);
    }

    @Override
    public File getContentDirectory() {
        return contentDirectory;
    }

    @Override
    public void setContentDirectory(File file) {
        this.contentDirectory = file;
    }

    private void validateNamespace(NamespaceIdentified identified) {
        if (!identified.getId().getNamespace().equals(namespace)) {
            throw new IllegalArgumentException("The namespace of NamespacedId " + identified.getId() + " must match the registry namespace " + namespace);
        }
    }

}
