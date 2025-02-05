package com.app.campaignapi.mappers;

public interface Mapper<A, B> {
    B mapToDto(A a);

    A mapToEntity(B b);
}
