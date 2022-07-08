package cat.kmruiz.mdiag.overview.topology.sharded;

import java.time.Duration;

public record ShardMongos(
        String id,
        String mongoVersion,
        Duration uptime,
        boolean waiting
) {
}
