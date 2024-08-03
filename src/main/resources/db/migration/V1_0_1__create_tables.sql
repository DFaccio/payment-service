CREATE TABLE IF NOT EXISTS payment
  (
     paymentId          SERIAL NOT NULL,
     cpf                VARCHAR(255) NOT NULL,
     cardnumber         VARCHAR(255) NOT NULL,
     paymentmethod      VARCHAR(255) NOT NULL,
     paymentdescription VARCHAR(255) NOT NULL,
     paymentvalue       BIGINT NOT NULL,
     transactiondate    TIMESTAMP NOT NULL,
     cardTransactionId  SERIAL NOT NULL,
     paymentStatus      VARCHAR(255) NOT NULL,
     PRIMARY KEY (paymentId)
  );