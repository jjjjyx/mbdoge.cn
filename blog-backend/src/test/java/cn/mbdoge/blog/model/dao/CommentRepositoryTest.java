package cn.mbdoge.blog.model.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryTest {
    @Test
    public void name() {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.id = 1;
        user.name = "12312";

        Item item = new Item();
        item.id = 100;
        item.itemName = "itemname";
        item.owner = user;

        Item item2 = new Item();
        item2.id = 100;
        item2.itemName = "itemname";

        user.userItems = Arrays.asList(item, item2);

        try {

            System.out.println("objectMapper.writeValueAsString(user) = " + objectMapper.writeValueAsString(user));
            System.out.println("objectMapper = " + objectMapper.writeValueAsString(item));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
class User {
    public int id;
    public String name;

    @JsonManagedReference
    public List<Item> userItems;
}

class Item {
    public int id;
    public String itemName;


    @JsonBackReference
    public User owner;
}