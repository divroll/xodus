/**
 * Copyright 2010 - 2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.exodus.vfs;

import jetbrains.exodus.ArrayByteIterable;
import jetbrains.exodus.ByteIterable;
import jetbrains.exodus.ByteIterator;
import jetbrains.exodus.bindings.LongBinding;
import jetbrains.exodus.util.LightOutputStream;
import org.jetbrains.annotations.NotNull;

class ClusterKey {

    private final long fd;
    private final long clusterNumber;

    ClusterKey(@NotNull final ByteIterable iterable) {
        final ByteIterator iterator = iterable.iterator();
        fd = LongBinding.readCompressed(iterator);
        clusterNumber = LongBinding.readCompressed(iterator);
    }

    long getDescriptor() {
        return fd;
    }

    long getClusterNumber() {
        return clusterNumber;
    }

    static ArrayByteIterable toByteIterable(final long fd, final long clusterNumber) {
        final LightOutputStream output = new LightOutputStream(8);
        final int[] bytes = new int[8];
        LongBinding.writeCompressed(output, fd, bytes);
        LongBinding.writeCompressed(output, clusterNumber, bytes);
        return output.asArrayByteIterable();
    }
}
