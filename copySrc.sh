#!/bin/zsh

# Copy our source over to the VM
rsync -avz src/main/java/ ece651:~/assignments/s24-hwk2-charlieprior/app/src/main/java
rsync -avz src/test/java/ ece651:~/assignments/s24-hwk2-charlieprior/app/src/test/java

# Copy test files back over here
rsync -avz ece651:~/assignments/s24-hwk2-charlieprior/app/src/test/resources/ src/test/resources