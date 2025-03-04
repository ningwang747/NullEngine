package nullengine.world.chunk;

import nullengine.block.AirBlock;
import nullengine.block.Block;
import nullengine.entity.Entity;
import nullengine.event.world.block.cause.BlockChangeCause;
import nullengine.math.BlockPos;
import nullengine.world.World;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BlankChunk implements Chunk {

    private final World world;
    private final int chunkX;
    private final int chunkY;
    private final int chunkZ;

    private final Vector3fc min;
    private final Vector3fc max;

    private final List<Entity> entities = new ArrayList<>();

    public BlankChunk(World world, int chunkX, int chunkY, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        this.min = new Vector3f(chunkX << ChunkConstants.BITS_X, chunkY << ChunkConstants.BITS_Y, chunkZ << ChunkConstants.BITS_Z);
        this.max = min.add(16, 16, 16, new Vector3f());
    }

    @Nonnull
    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getChunkX() {
        return chunkX;
    }

    @Override
    public int getChunkY() {
        return chunkY;
    }

    @Override
    public int getChunkZ() {
        return chunkZ;
    }

    @Nonnull
    @Override
    public Vector3fc getMin() {
        return min;
    }

    @Nonnull
    @Override
    public Vector3fc getMax() {
        return max;
    }


    @Nonnull
    @Override
    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return AirBlock.AIR;
    }

    @Override
    public int getBlockId(int x, int y, int z) {
        return 0;
    }

    @Override
    public Block setBlock(@Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockChangeCause cause) {
        return AirBlock.AIR;
    }

    @Override
    public boolean isAirChunk() {
        return true;
    }
}
