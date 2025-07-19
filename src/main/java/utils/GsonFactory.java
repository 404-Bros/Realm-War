package utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.blocks.*;
import models.structures.*;
import models.units.*;

public class GsonFactory {
    public static Gson create() {
        RuntimeTypeAdapterFactory<Block> blockFactory =
            RuntimeTypeAdapterFactory.of(Block.class, "type")
                .registerSubtype(EmptyBlock.class, "emptyBlock")
                .registerSubtype(ForestBlock.class, "forestBlock")
                .registerSubtype(VoidBlock.class, "voidBlock");

        RuntimeTypeAdapterFactory<Structure> structureFactory =
            RuntimeTypeAdapterFactory.of(Structure.class, "type")
                .registerSubtype(TownHall.class, "townHall")
                .registerSubtype(Barrack.class, "barrack")
                .registerSubtype(Farm.class, "farm")
                .registerSubtype(Market.class, "market")
                .registerSubtype(Tower.class, "tower");

        RuntimeTypeAdapterFactory<Unit> unitFactory =
            RuntimeTypeAdapterFactory.of(Unit.class, "type")
                .registerSubtype(Knight.class, "knight")
                .registerSubtype(Peasant.class, "peasant")
                .registerSubtype(Spearman.class, "spearman")
                .registerSubtype(Swordman.class, "swordman");

        return new GsonBuilder()
            .registerTypeAdapterFactory(blockFactory)
            .registerTypeAdapterFactory(structureFactory)
            .registerTypeAdapterFactory(unitFactory)
            .create();
    }
}
