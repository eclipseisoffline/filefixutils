package xyz.eclipsesioffline.filefixutils.neoforge.mixin;

import com.mojang.datafixers.DataFixerBuilder;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.filefix.FileFixerUpper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.eclipseisoffline.filefixutils.api.FileFixInitializer;

import java.util.ServiceLoader;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {

    @Inject(method = "addFixers", at = @At("HEAD"))
    private static void callInitializers(DataFixerBuilder fixerUpper, FileFixerUpper.Builder fileFixerUpper, CallbackInfo callbackInfo) {
        ServiceLoader<FileFixInitializer> fileFixInitializers = ServiceLoader.load(FileFixInitializer.class);
        for (FileFixInitializer fileFixInitializer : fileFixInitializers) {
            fileFixInitializer.onFileFixPopulate();
        }
    }
}
