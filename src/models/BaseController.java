package models;

import java.util.Observable;

/**
 * Created by 0940135 on 2016-03-04.
 */
public abstract class BaseController{

    public abstract void dispatch(String input, ServeurSSL.ClientSSLThread origin);
}
