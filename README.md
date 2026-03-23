# File Fix Utils

![GitHub License](https://img.shields.io/github/license/eclipseisoffline/filefixutils)
![Maven version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.eclipseisoffline.xyz%2Freleases%2Fxyz%2Feclipseisoffline%2Ffilefixutils-common%2Fmaven-metadata.xml)

File Fix Utils is a simple modloader-agnostic API for Minecraft's `FileFix` system.

Artefacts are available at [maven.eclipseisoffline.xyz](https://maven.eclipseisoffline.xyz).

## Usage

Include this library in your project as follows:

```kotlin
repositories {
    maven {
        name = "eclipseisoffline"
        url = uri("https://maven.eclipseisoffline.xyz/releases")
    }
}

dependencies {
    // For your common module:
    implementation("xyz.eclipseisoffline:filefixutils-common:0.1.0-26.1")
    
    // For your Fabric module:
    implementation("xyz.eclipseisoffline:filefixutils-fabric:0.1.0-26.1")
    
    // For your NeoForge module:
    implementation("xyz.eclipseisoffline:filefixutils-neoforge:0.1.0-26.1")
}
```

On MultiMod setups, you can use and include the library in your mods as follows:

```kotlin
multimod {
    settings {
        repositories {
            maven {
                name = "eclipseisoffline"
                url = uri("https://maven.eclipseisoffline.xyz/releases")
            }
        }
    }

    sharedDependencies {
        multiModInclude(multiModImplementation("xyz.eclipseisoffline:filefixutils:0.1.0-26.1"))
    }
}
```

Once you've included the library in your project, depend on it in your `fabric.mod.json`:

```json
{
  "depends": {
    "filefixutils": "*"
  }
}
```

And in your `neoforge.mods.toml`:

```toml
[[dependencies]]
    modId="filefixutils"
    type="required"
    ordering="AFTER"
    side="BOTH"
```

Once you've done that, you can use the library in your code. Please note that on Fabric, you need to register file fixes
in a `preLaunch` entrypoint.
