package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.ImageService;
import com.epam.eventapp.service.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Test class for image controller
 */
public class ImageControllerTest {

    @Mock
    private UserService userServiceMock;

    @Mock
    private ImageService imageServiceMock;

    private MockMvc mockMvc;

    @InjectMocks
    private ImageController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }



    /**
     * Testing an ability to save an image to user folder
     */
    @Test
    public void shouldSaveUserImage() throws Exception {

        //given
        String username = "testuser";
        final String DIRECTORY_PATH = "\\\\EPRUPETW0518\\images\\users\\testuser\\";
        byte[] fileContent = new byte[] {1,3,4,9,0,3,5,9,0,3,4,5};

        when(userServiceMock.updateUserPhotoByUsername(username, DIRECTORY_PATH + username)).thenReturn(1);
        when(imageServiceMock.saveUserImage(any(),any())).thenReturn("");

        //when
        ResultActions resultActions = mockMvc.perform(fileUpload("/image/user/" + username)
                                                        .file("users_photo", fileContent));

        //then
        resultActions.andExpect(status().isNoContent());

    }

    /**
     *
     */
    @Test
    public void shouldGetUserPhoto() throws Exception {

        //given
        String username = "testuser";
        String photoURL    = "\\\\EPRUPETW0518\\images\\users\\testuser\\";
        byte[] photo = new byte[] {1,0,0,0,0,0};
        User user = User.builder().photo(photoURL).build();

        when(imageServiceMock.getUserPhoto(photoURL,username)).thenReturn(photo);
        when(userServiceMock.getUserByUsername(username)).thenReturn(user);

        //when
        ResultActions resultActions = mockMvc.perform(get("/image/user/" + username));

        //then
        //no exceptions are thrown


    }



}
