package com.fabrik.api.common.domain;

import java.util.List;

public record PayloadListResponse<T>(List<T> list) {}