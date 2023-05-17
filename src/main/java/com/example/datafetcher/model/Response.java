package com.example.datafetcher.model;

import java.util.List;

public abstract class Response {
    public abstract List<? extends EntityWithOwner> getItems();
}
