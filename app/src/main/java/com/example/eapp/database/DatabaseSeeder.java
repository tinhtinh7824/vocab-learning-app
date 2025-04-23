package com.example.eapp.database;

import android.content.Context;
import com.example.eapp.models.Word;
import java.util.Arrays;
import java.util.List;

public class DatabaseSeeder {

    public static void insertSampleWords(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);  // Lấy instance của AppDatabase

        // Tạo danh sách từ vựng mẫu cho 6 chủ đề
        List<Word> words = Arrays.asList(
                // Trái cây
                new Word("Apple", "Táo", 1, "/ˈæpl/", "I ate an apple for breakfast."),
                new Word("Banana", "Chuối", 1, "/bəˈnænə/", "I have a banana every morning."),
                new Word("Orange", "Cam", 1, "/ˈɔːrɪndʒ/", "She drank fresh orange juice."),
                new Word("Mango", "Xoài", 1, "/ˈmæŋɡəʊ/", "I love eating mango in the summer."),
                new Word("Pineapple", "Dứa", 1, "/ˈpaɪnˌæpəl/", "Pineapple is rich in vitamin C."),
                new Word("Grape", "Nho", 1, "/ɡreɪp/", "He brought grapes for dessert."),
                new Word("Watermelon", "Dưa hấu", 1, "/ˈwɔːtərˌmɛlən/", "Watermelon is very refreshing."),
                new Word("Peach", "Đào", 1, "/piːtʃ/", "She ate a juicy peach."),
                new Word("Pear", "Lê", 1, "/per/", "The pear was sweet and ripe."),
                new Word("Strawberry", "Dâu tây", 1, "/ˈstrɔːberi/", "I love strawberry ice cream."),
                // Động vật
                new Word("Dog", "Chó", 2, "/dɒɡ/", "The dog barked loudly."),
                new Word("Cat", "Mèo", 2, "/kæt/", "My cat loves to sleep on the couch."),
                new Word("Elephant", "Voi", 2, "/ˈɛlɪfənt/", "Elephants are the largest land animals."),
                new Word("Tiger", "Hổ", 2, "/ˈtaɪɡər/", "The tiger roared loudly in the jungle."),
                new Word("Lion", "Sư tử", 2, "/ˈlaɪən/", "The lion is known as the king of the jungle."),
                new Word("Giraffe", "Hươu cao cổ", 2, "/dʒɪˈræf/", "The giraffe has a very long neck."),
                new Word("Bear", "Gấu", 2, "/beər/", "Bears hibernate during the winter."),
                new Word("Monkey", "Khỉ", 2, "/ˈmʌŋki/", "The monkey swung from tree to tree."),
                new Word("Zebra", "Ngựa vằn", 2, "/ˈzɛbrə/", "Zebras are known for their black and white stripes."),
                new Word("Panda", "Gấu trúc", 2, "/ˈpændə/", "The panda is native to China."),
                // Màu sắc
                new Word("Red", "Đỏ", 3, "/rɛd/", "She wore a red dress to the party."),
                new Word("Blue", "Xanh dương", 3, "/bluː/", "The sky is blue today."),
                new Word("Green", "Xanh lá", 3, "/ɡriːn/", "The grass is green in the spring."),
                new Word("Yellow", "Vàng", 3, "/ˈjɛləʊ/", "The sun is yellow."),
                new Word("Orange", "Cam", 3, "/ˈɔːrɪndʒ/", "She painted the wall orange."),
                new Word("Pink", "Hồng", 3, "/pɪŋk/", "She likes the color pink."),
                new Word("Purple", "Tím", 3, "/ˈpɜːrpl/", "He wore a purple shirt."),
                new Word("Brown", "Nâu", 3, "/braʊn/", "The tree bark is brown."),
                new Word("Black", "Đen", 3, "/blæk/", "The cat is black."),
                new Word("White", "Trắng", 3, "/waɪt/", "The snow is white."),
                // Số đếm
                new Word("One", "Một", 4, "/wʌn/", "One apple a day keeps the doctor away."),
                new Word("Two", "Hai", 4, "/tuː/", "There are two cars in the parking lot."),
                new Word("Three", "Ba", 4, "/θriː/", "Three students are absent today."),
                new Word("Four", "Bốn", 4, "/fɔːr/", "I have four pens in my bag."),
                new Word("Five", "Năm", 4, "/faɪv/", "I have five brothers."),
                new Word("Six", "Sáu", 4, "/sɪks/", "Six months ago, I went to Paris."),
                new Word("Seven", "Bảy", 4, "/ˈsɛvən/", "Seven days in a week."),
                new Word("Eight", "Tám", 4, "/eɪt/", "There are eight slices of pizza."),
                new Word("Nine", "Chín", 4, "/naɪn/", "I see nine birds in the sky."),
                new Word("Ten", "Mười", 4, "/tɛn/", "Ten is a round number."),
                // Nghề nghiệp
                new Word("Doctor", "Bác sĩ", 5, "/ˈdɒktər/", "The doctor is treating a patient."),
                new Word("Teacher", "Giáo viên", 5, "/ˈtiːtʃər/", "The teacher gave us a homework assignment."),
                new Word("Engineer", "Kỹ sư", 5, "/ˌɛnˈdʒɪnɪər/", "The engineer built a bridge."),
                new Word("Farmer", "Nông dân", 5, "/ˈfɑːrmər/", "The farmer grows vegetables."),
                new Word("Nurse", "Y tá", 5, "/nɜːrs/", "The nurse helped the patient."),
                new Word("Lawyer", "Luật sư", 5, "/ˈlɔːjər/", "The lawyer defended his client."),
                new Word("Actor", "Diễn viên", 5, "/ˈæktər/", "The actor starred in a movie."),
                new Word("Chef", "Đầu bếp", 5, "/ʃɛf/", "The chef cooked a delicious meal."),
                new Word("Artist", "Nghệ sĩ", 5, "/ˈɑːrtɪst/", "The artist painted a beautiful picture."),
                new Word("Scientist", "Nhà khoa học", 5, "/ˈsaɪəntɪst/", "The scientist conducted an experiment."),
                // Phương tiện giao thông
                new Word("Car", "Ô tô", 6, "/kɑːr/", "I drive a car to work."),
                new Word("Bus", "Xe buýt", 6, "/bʌs/", "The bus is very crowded."),
                new Word("Train", "Tàu", 6, "/treɪn/", "We took a train to the city."),
                new Word("Airplane", "Máy bay", 6, "/ˈɛəpleɪn/", "The airplane landed safely."),
                new Word("Bicycle", "Xe đạp", 6, "/ˈbaɪsɪkəl/", "She rode her bicycle to school."),
                new Word("Boat", "Thuyền", 6, "/boʊt/", "The boat sailed across the lake."),
                new Word("Motorcycle", "Xe máy", 6, "/ˈmoʊtərˌsaɪkəl/", "He rode his motorcycle to work."),
                new Word("Tram", "Xe điện", 6, "/træm/", "The tram takes us to the city center."),
                new Word("Helicopter", "Trực thăng", 6, "/ˈhɛlɪkɒptər/", "The helicopter flew over the mountains."),
                new Word("Subway", "Tàu điện ngầm", 6, "/ˈsʌbweɪ/", "The subway is very fast.")
        );

        // Chèn các từ vựng vào cơ sở dữ liệu nếu chưa có
        new Thread(() -> {
            for (Word word : words) {
                // Kiểm tra nếu từ vựng đã có trước khi chèn
                if (db.wordDao().getWordByName(word.getWord()) == null) {
                    db.wordDao().insertWord(word);  // Chèn từ vựng vào database nếu chưa có
                }
            }
        }).start();
    }
}
