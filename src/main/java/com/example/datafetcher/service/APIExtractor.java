package com.example.datafetcher.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIExtractor {
    private static final Pattern API_PATTERN = Pattern.compile("(?<=\\s)java\\.\\w+(?=\\s)");
}

