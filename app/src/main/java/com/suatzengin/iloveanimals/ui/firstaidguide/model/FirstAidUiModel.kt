package com.suatzengin.iloveanimals.ui.firstaidguide.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.suatzengin.iloveanimals.R
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class FirstAidUiModel(
    val title: String,
    val thumbnailResId: Int,
    val detailImageResId: Int,
    val stepList: List<String>
) : Parcelable

val firstAidGuides: List<FirstAidUiModel>
    get() = listOf(
        FirstAidUiModel(
            title = "Sıcak Çarpması",
            thumbnailResId = R.drawable.ic_sun,
            detailImageResId = R.drawable.ic_air_conditioned_dog,
            stepList = listOf(
                "Evcil hayvanınızı güneşten uzak, serin, gölgeli bir alana veya vantilatörün yakınındaki serin fayansların üzerine getirin. Klimalı oda idealdir.",
                "Başını, vücudunu ve bacaklarını nazikçe soğutmak için soğuk su veya ıslak havlu kullanın ve evcil hayvanınızın susuz kalmasına yardımcı olmak için az miktarda su içirin. Evcil hayvanınızın çevresinde iyi bir hava akışı olduğundan emin olun.",
                "Belirtiler azalmış olsa bile derhal veterinerinize başvurmayı unutmayın."
            )
        ),
        FirstAidUiModel(
            title = "Zehirlenme",
            thumbnailResId = R.drawable.ic_plant_toxicity,
            detailImageResId = R.drawable.ic_cat_toxicity,
            stepList = listOf(
                "Evcil hayvanınızın zehir yuttuğunu düşünüyorsanız veya toksinleri temizlemek için nemli bir havlu veya yüz bezi kullanın. İyi tolere edilirse ağızlarını nazikçe silin.",
                "Her türlü zehirlenmede, veterineriniz tarafından talimat verilmedikçe, evcil hayvanınızı kusturmak için yiyecek, su veya başka herhangi bir madde vermeyin.",
                "Tüketilen her türlü malzemeyi toplayın veya kaydedin ve evcil hayvanınızı derhal veterinere götürün."
            )
        ),
        FirstAidUiModel(
            title = "Zehirli Isırıklar",
            thumbnailResId = R.drawable.ic_snake_bites,
            detailImageResId = R.drawable.ic_emergency_vet_dog,
            stepList = listOf(
                "Evcil hayvanınızı hareketsiz hale getirin ve mümkün olduğunca hareketsiz ve sessiz tutun. İyi tolere edilirse, bir havluya sarmanız gerekebilir.",
                "Evcil hayvanınız bir böcek tarafından sokulduysa, serin bir yüz bezi sokulan yerdeki ağrıyı hafifletmeye yardımcı olabilir.",
                "Evcil hayvanınızı derhal en yakın veterinere götürün. Anti-venom mevcut olabilir ve hayat kurtarıcı olabilir. Evcil hayvanınızı neyin ısırdığını tespit etmek çok önemlidir."
            )
        ),
        FirstAidUiModel(
            title = "Travma ve Kanama",
            thumbnailResId = R.drawable.ic_injured_cat,
            detailImageResId = R.drawable.ic_injured_cat_call_vet,
            stepList = listOf(
                "Evcil hayvanınızın kanaması varsa, yaraya temiz bir bez veya gazlı bezle doğrudan basınç uygulayın.",
                "Hayvana araba çarptıysa, kırık kemikler veya iç yaralanmalar olabileceğinin farkında olun. Mümkünse, veterinere götürmek için dikkatlice bir tahta veya battaniye üzerine taşıyın. Bu hareket ve acıyı en aza indirecektir.",
                "Evcil hayvanınız yere yığılırsa, solunum yollarının açık olup olmadığını kontrol edin ve hayvanı yukarıdaki talimatlara uygun olarak derhal en yakın veterinere götürün."
            )
        ),
        FirstAidUiModel(
            title = "Boğulma",
            thumbnailResId = R.drawable.ic_choking_dog,
            detailImageResId = R.drawable.ic_help_choking_dog,
            stepList = listOf(
                "Evcil hayvanınız nefes alamıyorsa veya yere yığılmışsa, ilk yardım uygulamadan önce derhal veterineri arayın.",
                "Evcil hayvanınızın boğazında bir şey görebiliyorsanız, bir pense veya cımbızla tutmaya çalışın. Nesneyi daha geriye itmemek veya ısırılmamak için son derece dikkatli olun.",
                "Küçük hayvanlar, nesnenin yerinden çıkmasına yardımcı olmak için arka ayaklarından nazikçe kaldırılabilir. Daha büyük köpekler 'el arabası' tarzı bir hareketle sağrılarından nazikçe kaldırılabilir. Kürek kemikleri arasına 4-5 darbe vurmak için elinizin topuğunu kullanın, ancak aşırı güç kullanmamaya çok dikkat edin. Cisim evcil hayvanınızın ağzından veya boğazından çıkmış olsa bile, uygun şekilde değerlendirilmesi için her zaman veterinere götürün."
            )
        ),
        FirstAidUiModel(
            title = "Keneler",
            thumbnailResId = R.drawable.ic_tick,
            detailImageResId = R.drawable.ic_help_ticking_cat,
            stepList = listOf(
                "Keneleri erken bulmak çok önemlidir ve onları dokunarak bulma olasılığınız bakarak bulma olasılığınızdan daha yüksektir. Ayak parmaklarının arası da dahil olmak üzere evcil hayvanınızın her yerini nazikçe yoklayın.",
                "Keneyi mümkün olan en kısa sürede çıkarın ve kenenin tamamını çıkarmaya çalışın. Cımbız veya kene bükücü kullanın.",
                "Kenenin toksin kalıntısı hala felce neden olabileceğinden, iyi görünseler bile evcil hayvanınızı her zaman veterinerinize götürün. Mümkünse çıkardığınız keneyi de getirin."
            )
        ),
        FirstAidUiModel(
            title = "Nöbetler ve Kasılmalar",
            thumbnailResId = R.drawable.ic_sick_dog,
            detailImageResId = R.drawable.ic_sick_dog_call_vet,
            stepList = listOf(
                "Güvende kalırken, evcil hayvanınızı nöbet sırasında veya sonrasında kendini yaralamaktan koruyun. Örneğin, yüksekten düşmelerini engelleyin.",
                "Başka evcil hayvanlarınız varsa, bazı hayvanlar nöbet geçirdikten sonra agresifleşebileceğinden onları odadan veya alandan uzaklaştırın.",
                "Nöbetin nasıl başladığını, ne kadar sürdüğünü ve evcil hayvanınızın sergilediği davranışları not etmeye çalışın, böylece bu bilgileri veterinerle paylaşabilirsiniz.",
                "Veterinerinizi arayın ve evcil hayvanınızın mümkün olan en kısa sürede görülmesini sağlayın."
            )
        ),
    )
