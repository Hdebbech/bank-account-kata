package kata.bank.domain;

@FunctionalInterface
public interface TransactionIdGenerator {

    long generate();
}
