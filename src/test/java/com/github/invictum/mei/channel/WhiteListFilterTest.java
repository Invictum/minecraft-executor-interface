package com.github.invictum.mei.channel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import spark.HaltException;
import spark.Request;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class WhiteListFilterTest {

    @Mock
    private Request request;

    private List<String> ips = new ArrayList<>();
    private WhiteListFilter filter = new WhiteListFilter(ips);

    @Before
    public void beforeTest() {
        ips.clear();
        Mockito.when(request.ip()).thenReturn("127.0.0.1");
    }

    @Test
    public void validIp() {
        ips.add("127.0.0.1");
        filter.handle(request, null);
    }

    @Test
    public void emptyIpList() {
        filter.handle(request, null);
    }

    @Test(expected = HaltException.class)
    public void invalidIp() {
        ips.add("127.0.0.2");
        filter.handle(request, null);
    }
}
