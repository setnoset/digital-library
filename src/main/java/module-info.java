module Digital.Library {
    requires javafx.controls;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires org.hibernate.orm.core;
    requires org.hibernate.validator;
    requires java.sql;

    opens infrastructure.hibernate.model to org.hibernate.orm.core, org.hibernate.validator;

    exports presentation.javafx to javafx.graphics;
}