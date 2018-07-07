Hbase Example.

This project is meant to practice basic filter on hbase table.


Assumption : Hbase 2.0.1 version install on local machine.

Start hbase server and enter hbase shell from hbase directory using following commands.
```sybase
   ./bin/start-hbase.sh
   ./bin/hbase shell
```

create a transaction table
```sybase
    create "transaction" , "cf1"
```

