package xyz.eclipseisoffline.filefixutils.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.filefix.FileFixerUpper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.eclipseisoffline.filefixutils.api.FileFixSchemaRegister;

import java.util.function.BiFunction;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {

    @WrapOperation(method = "addFixers", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/filefix/FileFixerUpper$Builder;addSchema(Lcom/mojang/datafixers/DataFixerBuilder;ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;"))
    private static Schema runSchemaRegisterCallback(FileFixerUpper.Builder instance, DataFixerBuilder fixerUpper, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
        Schema schema = original.call(instance, fixerUpper, version, factory);
        FileFixSchemaRegister.call(instance, schema, version);
        return schema;
    }
}
