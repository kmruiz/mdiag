DIRECTORY=.mlaunch

stop:
	mlaunch kill --dir $(DIRECTORY) || true
	rm -rf $(DIRECTORY) || true

single: stop
	mlaunch init --single --dir $(DIRECTORY)

replica-set: stop
	mlaunch init --replicaset --dir $(DIRECTORY)

shard-s: stop
	mlaunch init --replicaset --sharded 2 --dir $(DIRECTORY)

shard-m: stop
	mlaunch init --replicaset  --sharded 4 --mongos 2 --dir $(DIRECTORY)

shard-l: stop
	mlaunch init --replicaset --sharded 6 --nodes 5 --mongos 6 --dir $(DIRECTORY)