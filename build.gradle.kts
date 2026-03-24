plugins {
    alias(libs.plugins.multimod)
}

group = properties["maven_group"] as String
version = properties["version"] as String

multimod {
    id = properties["mod_id"] as String
    name = properties["mod_name"] as String
    description = properties["mod_description"] as String

    archivesBaseName = properties["archives_base_name"] as String

    minecraft {
        minecraft = libs.minecraft
        supportedMinecraftVersions = ">=26.1-pre.1"
        neoForgeSupportedMinecraftVersions = "26.1"
    }

    neoForgeVersion = libs.versions.neoforge

    publishing {
        maven {
            name = "eclipseisoffline"
            url = uri("https://maven.eclipseisoffline.xyz/releases")
            credentials(PasswordCredentials::class)
        }
    }
}
