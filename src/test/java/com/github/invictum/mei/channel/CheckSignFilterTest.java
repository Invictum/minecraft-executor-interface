package com.github.invictum.mei.channel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import spark.HaltException;
import spark.Request;

import java.lang.reflect.Field;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CheckSignFilterTest {

    @Mock
    private Request request;

    private CheckSignFilter filter;

    @Before
    public void beforeTest() {
        filter = new CheckSignFilter("token", Logger.getGlobal());
    }

    @Test(expected = HaltException.class)
    public void invalidNonceHeader() {
        Mockito.when(request.headers(CheckSignFilter.NONCE_HEADER)).thenReturn("0");
        filter.handle(request, null);
    }

    @Test(expected = HaltException.class)
    public void absentNonceHeader() {
        Mockito.when(request.headers(CheckSignFilter.NONCE_HEADER)).thenReturn(null);
        filter.handle(request, null);
    }

    @Test(expected = HaltException.class)
    public void absentSighHeader() {
        Mockito.when(request.headers(Mockito.anyString())).thenReturn("1", "1", null);
        filter.handle(request, null);
    }

    @Test(expected = HaltException.class)
    public void wrongSighHeader() {
        Mockito.when(request.headers(Mockito.anyString())).thenReturn("1", "1", "sigh", "1", "sigh");
        filter.handle(request, null);
    }

    @Test
    public void sigh() {
        String sign = "5/wo3v6CxL1Duw0aJTAhWQcau1nc7yYMnO659E1/k+c=";
        Mockito.when(request.headers(Mockito.anyString())).thenReturn("1", "1", sign, "1", sign);
        filter.handle(request, null);
    }

    @Test
    public void nonceIncrement() throws Exception {
        String sigh = "5/wo3v6CxL1Duw0aJTAhWQcau1nc7yYMnO659E1/k+c=";
        Mockito.when(request.headers(Mockito.anyString())).thenReturn("1", "1", sigh, "1", sigh);
        filter.handle(request, null);
        /* Check nonce value with reflections */
        Field field = filter.getClass().getDeclaredField("lastNonce");
        field.setAccessible(true);
        Assert.assertEquals(1, field.getLong(filter));
    }
}
