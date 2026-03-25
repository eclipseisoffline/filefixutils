# File Fix Utils

![GitHub License](https://img.shields.io/github/license/eclipseisoffline/filefixutils)
![Maven version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.eclipseisoffline.xyz%2Freleases%2Fxyz%2Feclipseisoffline%2Ffilefixutils-common%2Fmaven-metadata.xml)

File Fix Utils is a simple modloader-agnostic API for Minecraft's `FileFix` system. It has helper methods to aid
the migration to namespaced saved-data in Minecraft 26.1.

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
    implementation("xyz.eclipseisoffline:filefixutils-common:0.1.2-26.1")
    
    // For your Fabric module:
    implementation("xyz.eclipseisoffline:filefixutils-fabric:0.1.2-26.1")
    
    // For your NeoForge module:
    implementation("xyz.eclipseisoffline:filefixutils-neoforge:0.1.2-26.1")
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
        multiModInclude(multiModImplementation("xyz.eclipseisoffline:filefixutils:0.1.2-26.1"))
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

Because Minecraft builds file fixes very early in the game loading process, a special entrypoint is required to be used
to register new file fixes. Your entrypoint needs to implement `FileFixInitializer`:

```java
public class FileFixRegisterer implements FileFixInitializer {

    @Override
    public void onFileFixPopulate() {
        // Register your file fixes HERE:
        FileFixSchemaRegister.register((fileFixerUpper, schema, version) -> {
            // code
        });
        // Helpers to register file fixes:
        FileFixHelpers.registerGlobalDataMoveFileFix("example_data", Identifier.fromNamespaceAndPath("example_mod", "example_data"));
    }
}
```

In your `fabric.mod.json`, you then need to add this entrypoint as follows:

```json
{
  "entrypoints": {
    "filefix": [
      "org.example.mod.FileFixRegisterer"
    ]
  }
}
```

On NeoForge, the entrypoint is run as a service. You need to add your class to the `META-INF/services/xyz.eclipseisoffline.filefixutils.api.FileFixInitializer` file in your project:

```text
# META-INF/services/xyz.eclipseisoffline.filefixutils.api.FileFixInitializer
org.example.mod.FileFixRegisterer
```

Once you've done that, your file fixes should be registered at start up. Of course, be sure to verify they actually work
when upgrading old worlds!
