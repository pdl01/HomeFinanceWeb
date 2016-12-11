CREATE TABLE registryTxns (
  id         VARCHAR(50) PRIMARY KEY,
  payee VARCHAR(50),
  statusText  VARCHAR(2),
  primaryAccount VARCHAR(50),
  secondaryAccount VARCHAR(50),
  txnType VARCHAR(10),
txnPersonalRefNumber VARCHAR(50),
txnExternalRefNumber VARCHAR(50),
txnDate VARCHAR(20),
createdDate BIGINT,
lastModifiedDate BIGINT,
credit BOOLEAN,
txnAmount DOUBLE,
memo VARCHAR(100),
flagged BOOLEAN,
flagComment VARCHAR(100)
);
