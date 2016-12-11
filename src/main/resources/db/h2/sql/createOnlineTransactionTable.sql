CREATE TABLE onlineTxns (
  id         VARCHAR(30) PRIMARY KEY,
  payee VARCHAR(30),
  statusText  VARCHAR(2),
  primaryAccount VARCHAR(30),
  secondaryAccount VARCHAR(30),
  txnType VARCHAR(10),
txnPersonalRefNumber VARCHAR(10),
txnExternalRefNumber VARCHAR(10),
txnDate VARCHAR(20),
createdDate BIGINT,
lastModifiedDate BIGINT,
credit BOOLEAN,
txnAmount DOUBLE,
memo VARCHAR(100),
flagged BOOLEAN,
flagComment VARCHAR(100),
    matches BOOLEAN,
    processedForCategories BOOLEAN,
    processedForMatches BOOLEAN

);

