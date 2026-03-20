package xyz.eclipseisoffline.filefixutils.api;

import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.filefix.FileFix;
import net.minecraft.util.filefix.FileFixerUpper;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class FileFixSchemaRegister {
    private static final List<Callback> callbacks = new ArrayList<>();
    private static boolean called = false;

    private FileFixSchemaRegister() {}

    public static void register(Callback callback) {
        if (called) {
            throw new IllegalStateException("Tried to register FileFixSchemaRegister.Callback after the event occurred! Make sure to register it before DataFixers are initialised");
        }
        callbacks.add(callback);
    }

    @ApiStatus.Internal
    public static void call(FileFixerUpper.Builder fileFixerUpper, Schema schema, int version) {
        called = true;
        for (Callback callback : callbacks) {
            callback.schemaRegistered(fileFixerUpper, schema, version);
        }
    }

    @SafeVarargs
    public static void registerFileFixes(int version, Function<Schema, FileFix>... fixes) {
        register((fileFixerUpper, schema, schemaVersion) -> {
            if (schemaVersion == version) {
                for (Function<Schema, FileFix> fix : fixes) {
                    fileFixerUpper.addFixer(fix.apply(schema));
                }
            }
        });
    }

    @FunctionalInterface
    public interface Callback {
        void schemaRegistered(FileFixerUpper.Builder fileFixerUpper, Schema schema, int version);
    }
}
