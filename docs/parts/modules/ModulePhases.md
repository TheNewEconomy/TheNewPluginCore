# Phases

During the module loading process, TNCP follows a cadence of
phases, which run various methods within the module interface.
This document is to outline when each phases is actived in order
to provide a clearer understanding of what methods each developer
should implement and when.

## Overview

The following provides links to the various phases, in the order they are
called.

- Initialization
- Loading
    - InitFiles
    - InitConfigurations
    - EnableIO
    - InitCommands
    - InitMenus
- Post
    - InitConsumables
- Running
    - Consumables
        - IOBackup
        - ModuleLoaded
        - ModuleUnloaded
        - ModuleDisabled
- Unload
    - RemoveMenus
    - RemoveCommands
    - BackupIO
    - DisableIO
    - RemoveConfigurations
    - RemoveFiles