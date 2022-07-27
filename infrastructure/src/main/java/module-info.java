module infrastructure {
  requires org.hibernate.orm.core;
  requires java.persistence;
  requires domain;
  requires spring.tx;
  requires spring.context;
  requires spring.data.jpa;
}