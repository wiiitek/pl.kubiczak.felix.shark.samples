package pl.kubiczak.felix.shark.samples.tests.functional;

class DuplicatedHeaderInResponseException extends RuntimeException {

  DuplicatedHeaderInResponseException(String msg) {
    super(msg);
  }
}
