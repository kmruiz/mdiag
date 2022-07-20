# mdiag

JavaFX application that summarises the topology of MongoDB clusters. It requires Java 18 to run.

This software is not developed or supported by MongoDB, it's an independent development. Software is distributed AS IS
and should be tested in a testing environment before running in production. 

This application is still in development, and it lacks decent quality checks and lots of useful functionalities.

| MongoDB Version | Tested | Should Work |
|-----------------|--------|-------------|
| 6.0             | yes    | yes         |
| 5.0             | yes    | yes         |
| 4.4             | no     | yes         |

## Download

You can download the latest release for your operating system in the [releases page](https://github.com/kmruiz/mdiag/releases). It is recommended to always verify
the signature of the release with the md5 file of your version.

## Features

* [x] Can connect to a MongoDB sharded cluster
* [x] Shows mongos instances in the cluster
* [x] Shows the primary of a shard
* [x] Shows secondaries of shards
* [ ] Shows sample documents of collections
* [ ] Shows arbiters of shards
* [ ] Topology of the configServer
* [ ] Shows analytics nodes of shards
* [ ] Shows unbalanced shards
* [ ] Shows a changelog of the topology
* [x] Shows collection information and their primary shards
* [x] Reports can be exported to JSON and imported again
* [ ] Support for replica sets
* [ ] Support for standalone mongod instances

Other features might be coming also if I need them.

## Images

### Initial Window

Allows you to specify the connection to a live cluster or to import a JSON report.

![Initial Window, where you can specify a connection to a cluster or to import a report.](docs/img/init-flow.png)

### Dashboard

Shows an overview of the current cluster.

![Dashboard that shows a view of the current cluster. The picture shows a sharded cluster, where each shard contains a three replica set](docs/img/live-report.png)

