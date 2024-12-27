# Minecraft ServerLink

# Installation
1. Set up a redis instance
2. Add the mod to all servers to be synced
3. For each server, set the following environment variables to configure the mod:

| Environment Variable  | Description                                          | Value                                |
|-----------------------|------------------------------------------------------|--------------------------------------|
| `REDIS_HOST`          | The hostname or IP address of Redis.                 | IP/Host Address                      |
| `REDIS_PORT`          | The port number Redis is listening on.               | redis default is `6379`              |
| `SERVER_NAME`         | A unique friendly name for the server, shown ingame. | A name like "Survival" or "Creative" |
| `SYNC_CHAT`           | Enable synchronization of player chat messages.      | `true` or `false`                    |
| `SYNC_DEATH_MESSAGES` | Enable synchronization of death messages.            | `true` or `false`                    |
| `SYNC_JOIN_LEAVE`     | Enable synchronization of player join/leave events.  | `true` or `false`                    |
