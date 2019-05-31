package de.bringmeister.controller

import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Test getAllProducts endpoint`() {
        mockMvc
                .perform(get("/products/"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Banana")))
                .andExpect(jsonPath("$[1].name", equalTo("Tomato")))
    }

    @Test
    fun `Test getProduct endpoint`() {
        mockMvc
                .perform(get("/products/43b105a0-b5da-401b-a91d-32237ae418e4"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", equalTo("Banana")))
                .andExpect(jsonPath("$.id", equalTo("43b105a0-b5da-401b-a91d-32237ae418e4")))
                .andExpect(jsonPath("$.description", equalTo("<p>The <b>banana</b> is an edible <a href=\"/wiki/Fruit\" title=\"Fruit\">fruit</a> – botanically a <a href=\"/wiki/Berry_(botany)\" title=\"Berry (botany)\">berry</a><sup id=\"cite_ref-purdue1_1-0\" class=\"reference\"><a href=\"#cite_note-purdue1-1\">[1]</a></sup><sup id=\"cite_ref-Armstrong_2-0\" class=\"reference\"><a href=\"#cite_note-Armstrong-2\">[2]</a></sup> – produced by several kinds of large <a href=\"/wiki/Herbaceous\" class=\"mw-redirect\" title=\"Herbaceous\">herbaceous</a> <a href=\"/wiki/Flowering_plant\" title=\"Flowering plant\">flowering plants</a> in the <a href=\"/wiki/Genus\" title=\"Genus\">genus</a> <i><a href=\"/wiki/Musa_(genus)\" title=\"Musa (genus)\">Musa</a></i>.<sup id=\"cite_ref-MW_3-0\" class=\"reference\"><a href=\"#cite_note-MW-3\">[3]</a></sup> In some countries, bananas used for cooking may be called <a href=\"/wiki/Cooking_banana\" title=\"Cooking banana\">plantains</a>, in contrast to <b>dessert bananas</b>. The fruit is variable in size, color and firmness, but is usually elongated and curved, with soft flesh rich in <a href=\"/wiki/Starch\" title=\"Starch\">starch</a> covered with a rind which may be green, yellow, red, purple, or brown when ripe. The fruits grow in clusters hanging from the top of the plant. Almost all modern edible <a href=\"/wiki/Parthenocarpy\" title=\"Parthenocarpy\">parthenocarpic</a> (seedless) bananas come from two wild species&nbsp;– <i><a href=\"/wiki/Musa_acuminata\" title=\"Musa acuminata\">Musa acuminata</a></i> and <i><a href=\"/wiki/Musa_balbisiana\" title=\"Musa balbisiana\">Musa balbisiana</a></i>. The <a href=\"/wiki/Binomial_nomenclature\" title=\"Binomial nomenclature\">scientific names</a> of most cultivated bananas are <i>Musa acuminata</i>, <i>Musa balbisiana</i>, and <i>Musa</i> × <i>paradisiaca</i> for the hybrid <i>Musa acuminata</i> × <i>M.&nbsp;balbisiana</i>, depending on their <a href=\"/wiki/Genome\" title=\"Genome\">genomic</a> constitution. The old scientific name <i>Musa sapientum</i> is no longer used.</p>")))
                .andExpect(jsonPath("$.sku", equalTo("BA-01")))
                .andExpect(jsonPath("$.prices", hasSize<Any>(2)))
    }

    @Test
    fun `Test getProduct with unit endpoint`() {
        mockMvc
                .perform(get("/products/43b105a0-b5da-401b-a91d-32237ae418e4?unit=package"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", equalTo("Banana")))
                .andExpect(jsonPath("$.id", equalTo("43b105a0-b5da-401b-a91d-32237ae418e4")))
                .andExpect(jsonPath("$.description", equalTo("<p>The <b>banana</b> is an edible <a href=\"/wiki/Fruit\" title=\"Fruit\">fruit</a> – botanically a <a href=\"/wiki/Berry_(botany)\" title=\"Berry (botany)\">berry</a><sup id=\"cite_ref-purdue1_1-0\" class=\"reference\"><a href=\"#cite_note-purdue1-1\">[1]</a></sup><sup id=\"cite_ref-Armstrong_2-0\" class=\"reference\"><a href=\"#cite_note-Armstrong-2\">[2]</a></sup> – produced by several kinds of large <a href=\"/wiki/Herbaceous\" class=\"mw-redirect\" title=\"Herbaceous\">herbaceous</a> <a href=\"/wiki/Flowering_plant\" title=\"Flowering plant\">flowering plants</a> in the <a href=\"/wiki/Genus\" title=\"Genus\">genus</a> <i><a href=\"/wiki/Musa_(genus)\" title=\"Musa (genus)\">Musa</a></i>.<sup id=\"cite_ref-MW_3-0\" class=\"reference\"><a href=\"#cite_note-MW-3\">[3]</a></sup> In some countries, bananas used for cooking may be called <a href=\"/wiki/Cooking_banana\" title=\"Cooking banana\">plantains</a>, in contrast to <b>dessert bananas</b>. The fruit is variable in size, color and firmness, but is usually elongated and curved, with soft flesh rich in <a href=\"/wiki/Starch\" title=\"Starch\">starch</a> covered with a rind which may be green, yellow, red, purple, or brown when ripe. The fruits grow in clusters hanging from the top of the plant. Almost all modern edible <a href=\"/wiki/Parthenocarpy\" title=\"Parthenocarpy\">parthenocarpic</a> (seedless) bananas come from two wild species&nbsp;– <i><a href=\"/wiki/Musa_acuminata\" title=\"Musa acuminata\">Musa acuminata</a></i> and <i><a href=\"/wiki/Musa_balbisiana\" title=\"Musa balbisiana\">Musa balbisiana</a></i>. The <a href=\"/wiki/Binomial_nomenclature\" title=\"Binomial nomenclature\">scientific names</a> of most cultivated bananas are <i>Musa acuminata</i>, <i>Musa balbisiana</i>, and <i>Musa</i> × <i>paradisiaca</i> for the hybrid <i>Musa acuminata</i> × <i>M.&nbsp;balbisiana</i>, depending on their <a href=\"/wiki/Genome\" title=\"Genome\">genomic</a> constitution. The old scientific name <i>Musa sapientum</i> is no longer used.</p>")))
                .andExpect(jsonPath("$.sku", equalTo("BA-01")))
                .andExpect(jsonPath("$.prices", hasSize<Any>(1)))
                .andExpect(jsonPath("$.prices[0].unit", equalTo("package")))
    }

    @Test
    fun `Test getProduct with unit endpoint where unit is not present in product`() {
        mockMvc
                .perform(get("/products/43b105a0-b5da-401b-a91d-32237ae418e4?unit=somefakeunit"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", equalTo("Banana")))
                .andExpect(jsonPath("$.id", equalTo("43b105a0-b5da-401b-a91d-32237ae418e4")))
                .andExpect(jsonPath("$.description", equalTo("<p>The <b>banana</b> is an edible <a href=\"/wiki/Fruit\" title=\"Fruit\">fruit</a> – botanically a <a href=\"/wiki/Berry_(botany)\" title=\"Berry (botany)\">berry</a><sup id=\"cite_ref-purdue1_1-0\" class=\"reference\"><a href=\"#cite_note-purdue1-1\">[1]</a></sup><sup id=\"cite_ref-Armstrong_2-0\" class=\"reference\"><a href=\"#cite_note-Armstrong-2\">[2]</a></sup> – produced by several kinds of large <a href=\"/wiki/Herbaceous\" class=\"mw-redirect\" title=\"Herbaceous\">herbaceous</a> <a href=\"/wiki/Flowering_plant\" title=\"Flowering plant\">flowering plants</a> in the <a href=\"/wiki/Genus\" title=\"Genus\">genus</a> <i><a href=\"/wiki/Musa_(genus)\" title=\"Musa (genus)\">Musa</a></i>.<sup id=\"cite_ref-MW_3-0\" class=\"reference\"><a href=\"#cite_note-MW-3\">[3]</a></sup> In some countries, bananas used for cooking may be called <a href=\"/wiki/Cooking_banana\" title=\"Cooking banana\">plantains</a>, in contrast to <b>dessert bananas</b>. The fruit is variable in size, color and firmness, but is usually elongated and curved, with soft flesh rich in <a href=\"/wiki/Starch\" title=\"Starch\">starch</a> covered with a rind which may be green, yellow, red, purple, or brown when ripe. The fruits grow in clusters hanging from the top of the plant. Almost all modern edible <a href=\"/wiki/Parthenocarpy\" title=\"Parthenocarpy\">parthenocarpic</a> (seedless) bananas come from two wild species&nbsp;– <i><a href=\"/wiki/Musa_acuminata\" title=\"Musa acuminata\">Musa acuminata</a></i> and <i><a href=\"/wiki/Musa_balbisiana\" title=\"Musa balbisiana\">Musa balbisiana</a></i>. The <a href=\"/wiki/Binomial_nomenclature\" title=\"Binomial nomenclature\">scientific names</a> of most cultivated bananas are <i>Musa acuminata</i>, <i>Musa balbisiana</i>, and <i>Musa</i> × <i>paradisiaca</i> for the hybrid <i>Musa acuminata</i> × <i>M.&nbsp;balbisiana</i>, depending on their <a href=\"/wiki/Genome\" title=\"Genome\">genomic</a> constitution. The old scientific name <i>Musa sapientum</i> is no longer used.</p>")))
                .andExpect(jsonPath("$.sku", equalTo("BA-01")))
                .andExpect(jsonPath("$.prices", hasSize<Any>(0)))
    }

    @Test
    fun `Test getProduct endpoint with product not present`() {
        mockMvc
                .perform(get("/products/43b105a0-b5da-401b-a91d-32"))
                .andDo(print())
                .andExpect(status().isNotFound)
    }
}