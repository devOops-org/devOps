package kr.seok.trans;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 15:04:07.033 [main] INFO org.elasticsearch.plugins.PluginsService - no modules loaded
 * 15:04:07.038 [main] INFO org.elasticsearch.plugins.PluginsService - loaded plugin [org.elasticsearch.index.reindex.ReindexPlugin]
 * 15:04:07.038 [main] INFO org.elasticsearch.plugins.PluginsService - loaded plugin [org.elasticsearch.join.ParentJoinPlugin]
 * 15:04:07.038 [main] INFO org.elasticsearch.plugins.PluginsService - loaded plugin [org.elasticsearch.percolator.PercolatorPlugin]
 * 15:04:07.038 [main] INFO org.elasticsearch.plugins.PluginsService - loaded plugin [org.elasticsearch.script.mustache.MustachePlugin]
 * 15:04:07.038 [main] INFO org.elasticsearch.plugins.PluginsService - loaded plugin [org.elasticsearch.transport.Netty4Plugin]
 * 15:04:07.097 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [force_merge], size [1], queue size [unbounded]
 * 15:04:07.099 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [fetch_shard_started], core [1], max [16], keep alive [5m]
 * 15:04:07.099 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [listener], size [4], queue size [unbounded]
 * 15:04:07.099 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [refresh], core [1], max [4], keep alive [5m]
 * 15:04:07.100 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [generic], core [4], max [128], keep alive [30s]
 * 15:04:07.100 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [warmer], core [1], max [4], keep alive [5m]
 * 15:04:07.103 [main] DEBUG org.elasticsearch.common.util.concurrent.QueueResizingEsThreadPoolExecutor - thread pool [_client_/search] will adjust queue by [50] when determining automatic queue size
 * 15:04:07.106 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [search], size [13], queue size [1k]
 * 15:04:07.107 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [flush], core [1], max [4], keep alive [5m]
 * 15:04:07.107 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [fetch_shard_store], core [1], max [16], keep alive [5m]
 * 15:04:07.107 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [management], core [1], max [5], keep alive [5m]
 * 15:04:07.107 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [get], size [8], queue size [1k]
 * 15:04:07.107 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [analyze], size [1], queue size [16]
 * 15:04:07.107 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [write], size [8], queue size [200]
 * 15:04:07.108 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [snapshot], core [1], max [4], keep alive [5m]
 * 15:04:07.108 [main] DEBUG org.elasticsearch.common.util.concurrent.QueueResizingEsThreadPoolExecutor - thread pool [_client_/search_throttled] will adjust queue by [50] when determining automatic queue size
 * 15:04:07.108 [main] DEBUG org.elasticsearch.threadpool.ThreadPool - created thread pool: name [search_throttled], size [1], queue size [100]
 * 15:04:07.155 [main] DEBUG io.netty.util.internal.logging.InternalLoggerFactory - Using SLF4J as the default logging framework
 * 15:04:07.157 [main] DEBUG io.netty.util.internal.PlatformDependent - Platform: MacOS
 * 15:04:07.158 [main] DEBUG io.netty.util.internal.PlatformDependent0 - -Dio.netty.noUnsafe: true
 * 15:04:07.158 [main] DEBUG io.netty.util.internal.PlatformDependent0 - sun.misc.Unsafe: unavailable (io.netty.noUnsafe)
 * 15:04:07.158 [main] DEBUG io.netty.util.internal.PlatformDependent0 - Java version: 8
 * 15:04:07.158 [main] DEBUG io.netty.util.internal.PlatformDependent0 - java.nio.DirectByteBuffer.<init>(long, int): unavailable
 * 15:04:07.158 [main] DEBUG io.netty.util.internal.PlatformDependent - -Dio.netty.tmpdir: /var/folders/nw/8x8_fp593wjcfj3j7g7l012h0000gn/T (java.io.tmpdir)
 * 15:04:07.158 [main] DEBUG io.netty.util.internal.PlatformDependent - -Dio.netty.bitMode: 64 (sun.arch.data.model)
 * 15:04:07.160 [main] DEBUG io.netty.util.internal.PlatformDependent - -Dio.netty.maxDirectMemory: -1 bytes
 * 15:04:07.160 [main] DEBUG io.netty.util.internal.PlatformDependent - -Dio.netty.uninitializedArrayAllocationThreshold: -1
 * 15:04:07.163 [main] DEBUG io.netty.util.internal.CleanerJava6 - java.nio.ByteBuffer.cleaner(): available
 * 15:04:07.163 [main] DEBUG io.netty.util.internal.PlatformDependent - -Dio.netty.noPreferDirect: false
 * 15:04:07.452 [main] DEBUG org.elasticsearch.discovery.zen.ElectMasterService - using minimum_master_nodes [-1]
 * 15:04:08.074 [main] DEBUG org.elasticsearch.client.transport.TransportClientNodesService - node_sampler_interval[5s]
 * 15:04:08.086 [main] DEBUG io.netty.channel.MultithreadEventLoopGroup - -Dio.netty.eventLoopThreads: 16
 * 15:04:08.107 [main] DEBUG io.netty.util.internal.InternalThreadLocalMap - -Dio.netty.threadLocalMap.stringBuilder.initialSize: 1024
 * 15:04:08.108 [main] DEBUG io.netty.util.internal.InternalThreadLocalMap - -Dio.netty.threadLocalMap.stringBuilder.maxSize: 4096
 * 15:04:08.117 [main] DEBUG io.netty.channel.nio.NioEventLoop - -Dio.netty.noKeySetOptimization: true
 * 15:04:08.118 [main] DEBUG io.netty.channel.nio.NioEventLoop - -Dio.netty.selectorAutoRebuildThreshold: 512
 * 15:04:08.125 [main] DEBUG io.netty.util.internal.PlatformDependent - org.jctools-core.MpscChunkedArrayQueue: unavailable
 * 15:04:08.160 [main] DEBUG io.netty.util.ResourceLeakDetector - -Dio.netty.leakDetection.level: simple
 * 15:04:08.160 [main] DEBUG io.netty.util.ResourceLeakDetector - -Dio.netty.leakDetection.targetRecords: 4
 * 15:04:08.163 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.numHeapArenas: 16
 * 15:04:08.163 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.numDirectArenas: 16
 * 15:04:08.163 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.pageSize: 8192
 * 15:04:08.163 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.maxOrder: 11
 * 15:04:08.163 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.chunkSize: 16777216
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.smallCacheSize: 256
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.normalCacheSize: 64
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.maxCachedBufferCapacity: 32768
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.cacheTrimInterval: 8192
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.cacheTrimIntervalMillis: 0
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.useCacheForAllThreads: true
 * 15:04:08.164 [main] DEBUG io.netty.buffer.PooledByteBufAllocator - -Dio.netty.allocator.maxCachedByteBuffersPerChunk: 1023
 * 15:04:08.220 [main] DEBUG org.elasticsearch.client.transport.TransportClientNodesService - adding address [{#transport#-1}{ZRmwnrmDTIOJTSl8rOS4ow}{127.0.0.1}{127.0.0.1:9200}]
 * 15:04:08.237 [main] DEBUG io.netty.channel.DefaultChannelId - -Dio.netty.processId: 14187 (auto-detected)
 * 15:04:08.240 [main] DEBUG io.netty.util.NetUtil - -Djava.net.preferIPv4Stack: false
 * 15:04:08.240 [main] DEBUG io.netty.util.NetUtil - -Djava.net.preferIPv6Addresses: false
 * 15:04:08.242 [main] DEBUG io.netty.util.NetUtil - Loopback interface: lo0 (lo0, 0:0:0:0:0:0:0:1%lo0)
 * 15:04:08.242 [main] DEBUG io.netty.util.NetUtil - Failed to get SOMAXCONN from sysctl and file /proc/sys/net/core/somaxconn. Default: 128
 * 15:04:08.244 [main] DEBUG io.netty.channel.DefaultChannelId - -Dio.netty.machineId: ac:de:48:ff:fe:00:11:22 (auto-detected)
 * 15:04:08.271 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.allocator.type: pooled
 * 15:04:08.271 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.threadLocalDirectBufferSize: 0
 * 15:04:08.271 [main] DEBUG io.netty.buffer.ByteBufUtil - -Dio.netty.maxThreadLocalCharBufferSize: 16384
 * 15:04:08.329 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.checkAccessible: true
 * 15:04:08.329 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.buffer.AbstractByteBuf - -Dio.netty.buffer.checkBounds: true
 * 15:04:08.330 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@1d9fc048
 * 15:04:08.337 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxCapacityPerThread: disabled
 * 15:04:08.337 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.maxSharedCapacityFactor: disabled
 * 15:04:08.337 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.linkCapacity: disabled
 * 15:04:08.337 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.ratio: disabled
 * 15:04:08.337 [elasticsearch[_client_][transport_worker][T#1]] DEBUG io.netty.util.Recycler - -Dio.netty.recycler.delayedQueue.ratio: disabled
 * 15:04:38.410 [main] DEBUG org.elasticsearch.client.transport.TransportClientNodesService - failed to connect to node [{#transport#-1}{ZRmwnrmDTIOJTSl8rOS4ow}{127.0.0.1}{127.0.0.1:9200}], ignoring...
 *
 * org.elasticsearch.transport.ConnectTransportException: [][127.0.0.1:9200] handshake_timeout[30s]
 * 	at org.elasticsearch.transport.TransportHandshaker.lambda$sendHandshake$1(TransportHandshaker.java:77)
 * 	at org.elasticsearch.common.util.concurrent.ThreadContext$ContextPreservingRunnable.run(ThreadContext.java:633)
 * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 * 	at java.lang.Thread.run(Thread.java:748)
 *
 * 15:05:08.416 [elasticsearch[_client_][generic][T#1]] DEBUG org.elasticsearch.client.transport.TransportClientNodesService - failed to connect to node [{#transport#-1}{ZRmwnrmDTIOJTSl8rOS4ow}{127.0.0.1}{127.0.0.1:9200}], ignoring...
 *
 * org.elasticsearch.transport.ConnectTransportException: [][127.0.0.1:9200] handshake_timeout[30s]
 * 	at org.elasticsearch.transport.TransportHandshaker.lambda$sendHandshake$1(TransportHandshaker.java:77)
 * 	at org.elasticsearch.common.util.concurrent.ThreadContext$ContextPreservingRunnable.run(ThreadContext.java:633)
 * 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
 * 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
 * 	at java.lang.Thread.run(Thread.java:748)
 */
@Slf4j
public class 클라이언트연결_TRANS {
    private static final String SINGLE_NODE_CLUSTER = "docker-cluster";
    private static final String MULTI_NODE_CLUSTER = "es-docker-cluster";

    public static void main(String[] args) {
        /* 클러스터 정보 설정 클래스 Settings */
        Settings settings = Settings.builder()
                .put("cluster.name", SINGLE_NODE_CLUSTER)
                /* 스니핑 기능 */
                .put("client.transport.sniff", true)
                .build();

        try {
            /* Transport 클라이언트에는 새로운 노드를 자동적으로 추가하거 기존 노드를 삭제할 수 있는 클러스터 스니핑 기능이 내장되어 있다. */
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    /* 스니필 기능이 활성화 되면 addTransportAddress 메서드를 호출해서 빌드된 노드 목록을 5초에 한 번씩 갱신해 최신 데이터로 관리해준다. */
                    .addTransportAddress(
                            new TransportAddress(
                                    InetAddress.getByName("127.0.0.1"), 9200));
            client.close();

        } catch (UnknownHostException e) {
            log.debug("[LOG] [클라이언트연결] [호스트 연결 오류]");
        }
    }
}
