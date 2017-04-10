package gatech.water_app.Junit;

import android.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.User;
import gatech.water_app.model.UserLoginTask;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;



/**
 * Created by samgilsonmac on 4/10/17.
 */

@RunWith(PowerMockRunner.class)
public class WaterAppJunits {

    @Mock
    ServerConnector MyServerConnector;
    @InjectMocks
    UserLoginTask loginTask;
    @Mock
    Base64 encodeToString;


    @PrepareForTest(ServerConnector.class)
    @Test
    public void testAttemptLoginInUserLoginTask() throws IOException {
        User nullUser = null;
        User inDatabase = new User("samgilson", "memes", "samgilson98@gmail.com");
        assertEquals(null, UserLoginTask.attemptLogin(nullUser));


        mockStatic(ServerConnector.class);
        when(ServerConnector.attemptLogin("yes", "yes")).thenThrow(IOException.class);
        when(ServerConnector.attemptLogin("samgilson98@gmail.com", "memes"))
                .thenReturn(new User("samgilson", "memes", "samgilson98@gmail.com"));

        User notInDatabase = new User("yes", "yes", "yes");
        User u = UserLoginTask.attemptLogin(notInDatabase);
        assertEquals(null, u);

        u = UserLoginTask.attemptLogin(inDatabase);
        assertEquals(inDatabase, u);


    }

}
