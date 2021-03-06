package moe.sunjiao.localizer

import org.osmdroid.util.GeoPoint
import moe.sunjiao.nominatim.Address
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 *
 * @author sun-jiao (孙娇）
 *  all polygons from OSM (except 九段线), and simplified with the polygon tool: http://polygons.openstreetmap.fr/
 *
 */

//原始数据中：
//building
//house_number
//road
//address29 
//allotments 
//isolated_dwelling      1-2 households
//hamlet                 small village with 100-200 people
//locality 
//farm 
//school
//neighbourhood          有时候是小区，有时候是社区
//suburb                 社区
//city_district
//town                   有时候是街道
//city                   有时候是街道, 有时候是区
//county                 区、县
//region                 有的地级市用region表示。
//state_district         州辖区，相当于地区级，地级市
//state
//country country_code    这两个表示国家是确定的

class CNLocalizer 
    (private var address: Address)
{
    //藏南 3202329
    private val SOUTH_TIBET : Array<GeoPoint> = arrayOf(
        GeoPoint(27.73,97.42),
        GeoPoint(27.73 ,  97.11	),
        GeoPoint(27.73 ,  97.09	),
        GeoPoint(27.74 ,  97.05	),
        GeoPoint(27.81 ,  97.04	),
        GeoPoint(27.8  ,  97.00	),
        GeoPoint(27.86 ,  96.97	),
        GeoPoint(27.88 ,  96.91	),
        GeoPoint(27.88 ,  96.8894	),
        GeoPoint(27.877,  96.8863	),
        GeoPoint(27.876,  96.8830	),
        GeoPoint(27.876,  96.8811	),
        GeoPoint(27.877,  96.8796	),
        GeoPoint(27.878,  96.8770	),
        GeoPoint(27.878,  96.8750	),
        GeoPoint(27.877,  96.8724	),
        GeoPoint(27.875,  96.8606	),
        GeoPoint(27.876,  96.8591	),
        GeoPoint(27.877,  96.8543	),
        GeoPoint(27.878,  96.8486	),
        GeoPoint(27.878,  96.8470	),
        GeoPoint(27.88 ,  96.8453	),
        GeoPoint(27.88 ,  96.8	),
        GeoPoint(27.93 ,  96.78	),
        GeoPoint(27.92 ,  96.76	),
        GeoPoint(27.95 ,  96.71	),
        GeoPoint(27.94 ,  96.68	),
        GeoPoint(28.05 ,  96.57	),
        GeoPoint(28.06 ,  96.49	),
        GeoPoint(28.11 ,  96.48	),
        GeoPoint(28.15 ,  96.44	),
        GeoPoint(28.14 ,  96.41	),
        GeoPoint(28.11 ,  96.41	),
        GeoPoint(28.13 ,  96.29	),
        GeoPoint(28.22 ,  96.27	),
        GeoPoint(28.18 ,  96.01	),
        GeoPoint(28.23 ,  95.93	),
        GeoPoint(28.23 ,  95.9	),
        GeoPoint(28.27 ,  95.89	),
        GeoPoint(28.28 ,  95.87	),
        GeoPoint(28.25 ,  95.69	),
        GeoPoint(28.2  ,  95.6	),
        GeoPoint(28.13 ,  95.4	),
        GeoPoint(27.96 ,  95.32	),
        GeoPoint(27.92 ,  95.28	),
        GeoPoint(27.82 ,  95.02	),
        GeoPoint(27.74 ,  94.91	),
        GeoPoint(27.6  ,  94.55	),
        GeoPoint(27.58 ,  94.53	),
        GeoPoint(27.59 ,  94.47	),
        GeoPoint(27.57 ,  94.45	),
        GeoPoint(27.58 ,  94.4	),
        GeoPoint(27.57 ,  94.28	),
        GeoPoint(27.42 ,  94.13	),
        GeoPoint(27.3  ,  93.98	),
        GeoPoint(27.18 ,  93.87	),
        GeoPoint(27.07 ,  93.86	),
        GeoPoint(27.02 ,  93.84	),
        GeoPoint(27.01 ,  93.75	),
        GeoPoint(26.93 ,  93.58	),
        GeoPoint(26.9  ,  93.23	),
        GeoPoint(26.87 ,  93.11	),
        GeoPoint(26.87 ,  93.05	),
        GeoPoint(26.9  ,  92.96	),
        GeoPoint(26.89 ,  92.77	),
        GeoPoint(26.94 ,  92.68	),
        GeoPoint(26.94 ,  92.57	),
        GeoPoint(26.9  ,  92.47	),
        GeoPoint(26.89 ,  92.32	),
        GeoPoint(26.84 ,  92.11	),
        GeoPoint(26.85 ,  92.11	),
        GeoPoint(26.85,91.439),
        GeoPoint(29.449,91.439),
        GeoPoint(29.449,97.4125)
    )
    //楚木惹争议区 2713482
    private val CHUMAR  : Array<GeoPoint> = arrayOf (
        GeoPoint(32.5942,78.6520),
        GeoPoint(32.5942,78.569383),
        GeoPoint(32.615723,78.569383),
        GeoPoint(32.617623,78.569364),
        GeoPoint(32.618423,78.569525),
        GeoPoint(32.619684,78.570072),
        GeoPoint(32.621945,78.570477),
        GeoPoint(32.623729,78.570667),
        GeoPoint(32.623824,78.571881),
        GeoPoint(32.624181, 78.57269),
        GeoPoint(32.624514,78.573308),
        GeoPoint(32.622278,78.576473),
        GeoPoint(32.621659,78.577543),
        GeoPoint(32.620993,78.578519),
        GeoPoint(32.620683,78.579804),
        GeoPoint(32.622961,78.580106),
        GeoPoint(32.623444, 78.58006),
        GeoPoint(32.624373,78.579851),
        GeoPoint(32.625164,78.579851),
        GeoPoint(32.626985, 78.58037),
        GeoPoint(32.627384,78.580328),
        GeoPoint(32.62797,78.579905),
        GeoPoint(32.628589,78.579253),
        GeoPoint(32.629272,78.578993),
        GeoPoint(32.630705,78.578342),
        GeoPoint(32.631584,78.578276),
        GeoPoint(32.632952,78.578049),
        GeoPoint(32.633929,78.578146),
        GeoPoint(32.634873,78.578146),
        GeoPoint(32.635948,78.578342),
        GeoPoint(32.636534,78.578635),
        GeoPoint(32.637869,78.580002),
        GeoPoint(32.639464,78.582282),
        GeoPoint(32.640572,78.583682),
        GeoPoint(32.641093,78.584073),
        GeoPoint(32.641516,78.584919),
        GeoPoint(32.642004,78.585342),
        GeoPoint(32.642069,78.588338),
        GeoPoint(32.640669,78.593841),
        GeoPoint(32.640865,78.596511),
        GeoPoint(32.641158, 78.59713),
        GeoPoint(32.641027,78.601331),
        GeoPoint(32.641125,78.601689),
        GeoPoint(32.642037,78.602991),
        GeoPoint(32.642493,78.604652),
        GeoPoint(32.642949,78.606606),
        GeoPoint(32.642949, 78.60882),
        GeoPoint(32.64246,78.610644),
        GeoPoint(32.642297, 78.61149),
        GeoPoint(32.641711,78.613411),
        GeoPoint(32.640995,78.615039),
        GeoPoint(32.637999,78.617872),
        GeoPoint(32.637706,78.618589),
        GeoPoint(32.637218,78.619305),
        GeoPoint(32.636371,78.620379),
        GeoPoint(32.635785,78.621389),
        GeoPoint(32.635134,78.622822),
        GeoPoint(32.634645,78.623766),
        GeoPoint(32.634254,78.626534),
        GeoPoint(32.634124,78.628325),
        GeoPoint(32.634808,78.629497),
        GeoPoint(32.635524,78.630246),
        GeoPoint(32.635908,78.631319),
        GeoPoint(32.635908,78.6520)
    )
    //斯诺乌山争议区 10885200
    private val SERUURRI  : Array<GeoPoint> = arrayOf(
        GeoPoint(32.6576951, 78.6539554),
        GeoPoint(32.6586345, 78.6548567),
        GeoPoint(32.6598629, 78.6576891),
        GeoPoint(32.6625365, 78.6676883),
        GeoPoint(32.6653907, 78.6716365),
        GeoPoint(32.6669441, 78.6748981),
        GeoPoint(32.669184,  78.6782026),
        GeoPoint(32.6730855, 78.6804342),
        GeoPoint(32.6787569, 78.6813354),
        GeoPoint(32.6813216, 78.6824512),
        GeoPoint(32.6830554, 78.6846828),
        GeoPoint(32.6841931, 78.6900687),
        GeoPoint(32.686866,  78.6934805),
        GeoPoint(32.6876606, 78.6971712),
        GeoPoint(32.6871188, 78.6989736),
        GeoPoint(32.6875884, 78.7027716),
        GeoPoint(32.6888886, 78.7057328),
        GeoPoint(32.688094,  78.707385),
        GeoPoint(32.6905681, 78.7105822),
        GeoPoint(32.6957869, 78.71423),
        GeoPoint(32.6973038, 78.7149811),
        GeoPoint(32.6993081, 78.7152815),
        GeoPoint(32.7005179, 78.7145948),
        GeoPoint(32.7022333, 78.7120843),
        GeoPoint(32.7031542, 78.7133503),
        GeoPoint(32.7038042, 78.7140584),
        GeoPoint(32.7038764, 78.7154531),
        GeoPoint(32.7040931, 78.7179851),
        GeoPoint(32.7048876, 78.7200022),
        GeoPoint(32.7046348, 78.7237358),
        GeoPoint(32.7046528, 78.7246156),
        GeoPoint(32.7053751, 78.7252807),
        GeoPoint(32.7066751, 78.7254953),
        GeoPoint(32.7085528, 78.7269115),
        GeoPoint(32.709275,  78.7279415),
        GeoPoint(32.7103403, 78.7284565),
        GeoPoint(32.7104667, 78.7304735),
        GeoPoint(32.7078667, 78.7361383),
        GeoPoint(32.7078667, 78.7380481),
        GeoPoint(32.7084626, 78.7409019),
        GeoPoint(32.7092389, 78.74264),
        GeoPoint(32.7058626, 78.7460303),
        GeoPoint(32.704689,  78.7457299),
        GeoPoint(32.7024139, 78.7447643),
        GeoPoint(32.6993442, 78.74264),
        GeoPoint(32.695747,  78.739516),
        GeoPoint(32.6527,	78.739516),
        GeoPoint(32.6527,	78.6539554)
    )
    //桑、葱莎、波林三多地区 2713676
    private val SANG_TSHONG_SA_PULAM_SUMDA  : Array<GeoPoint> = arrayOf(
        GeoPoint(31.115  ,79.41),
        GeoPoint(31.075  ,79.415),
        GeoPoint(31.07   ,79.39),
        GeoPoint(31.055  ,79.385),
        GeoPoint(31.055  ,79.375),
        GeoPoint(31.04   ,79.37),
        GeoPoint(31.03   ,79.355),
        GeoPoint(31.025  ,79.32),
        GeoPoint(31.015  ,79.31),
        GeoPoint(31.00  ,79.315),
        GeoPoint(31.00  ,79.33),
        GeoPoint(30.96   ,79.325),
        GeoPoint(30.97   ,79.29),
        GeoPoint(30.95   ,79.255),
        GeoPoint(30.955  ,79.22),
        GeoPoint(30.965  ,79.21),
        GeoPoint(30.98   ,79.215),
        GeoPoint(30.99   ,79.205),
        GeoPoint(31.005  ,79.205),
        GeoPoint(31.02   ,79.18),
        GeoPoint(31.02   ,79.17),
        GeoPoint(31.00  ,79.15),
        GeoPoint(31.005  ,79.125),
        GeoPoint(30.995  ,79.11),
        GeoPoint(30.995  ,79.09),
        GeoPoint(31.005  ,79.09),
        GeoPoint(31.01   ,79.075),
        GeoPoint(31.035  ,79.055),
        GeoPoint(31.045  ,79.025),
        GeoPoint(31.04   ,79.01),
        GeoPoint( 31.05  ,79.00	  ),
        GeoPoint(31.07   ,78.99),
        GeoPoint(31.085  ,78.995),
        GeoPoint(31.095  ,78.99),
        GeoPoint( 31.1   ,79.00	  ),
        GeoPoint(31.11   ,78.995),
        GeoPoint(31.115  ,79.005),
        GeoPoint(31.135  ,79.005),
        GeoPoint(31.14   ,78.99),
        GeoPoint(31.165  ,78.995),
        GeoPoint(31.175  ,78.99),
        GeoPoint(31.185  ,78.96),
        GeoPoint(31.195  ,78.96),
        GeoPoint(31.205  ,78.945),
        GeoPoint(31.215  ,78.95),
        GeoPoint(31.22   ,78.935),
        GeoPoint(31.215  ,78.93),
        GeoPoint(31.25   ,78.92),
        GeoPoint(31.26   ,78.895),
        GeoPoint(31.27   ,78.885),
        GeoPoint(31.285  ,78.885),
        GeoPoint(31.6200,79.0688)
    )
    //乌热、然冲、拉不底地区 2713484 ,然冲的藏语名没找到，我用然乌湖 Rakwa Tso 和冲赛康 Tromzikhang 拼起来的，有人有正确的名称欢迎告知。
    private val WUJE_RAKTROM_LAPTHAL  : Array<GeoPoint> = arrayOf(
        GeoPoint(30.59   , 80.21	),
        GeoPoint(30.59   , 80.195   ),
        GeoPoint(30.575  , 80.175   ),
        GeoPoint(30.575  , 80.15    ),
        GeoPoint(30.56   , 80.14    ),
        GeoPoint(30.56   , 80.125   ),
        GeoPoint(30.57   , 80.085   ),
        GeoPoint(30.595  , 80.075   ),
        GeoPoint(30.595  , 80.05    ),
        GeoPoint(30.61   , 80.04    ),
        GeoPoint(30.61   , 80.03    ),
        GeoPoint(30.625  , 80.035   ),
        GeoPoint(30.64   , 80.03    ),
        GeoPoint(30.645  , 80.015   ),
        GeoPoint(30.665  , 80.015   ),
        GeoPoint(30.69   , 79.985   ),
        GeoPoint(30.75   , 79.985   ),
        GeoPoint(30.77   , 79.95    ),
        GeoPoint(30.775  , 79.96    ),
        GeoPoint(30.79   , 79.94    ),
        GeoPoint(30.8    , 79.895   ),
        GeoPoint(30.815  , 79.89    ),
        GeoPoint(30.82   , 79.91    ),
        GeoPoint(30.84   , 79.91    ),
        GeoPoint(30.845  , 79.89    ),
        GeoPoint(30.86   , 79.89    ),
        GeoPoint(30.865  , 79.875   ),
        GeoPoint(30.85   , 79.825   ),
        GeoPoint(30.885  , 79.8	),
        GeoPoint(30.9    , 79.8	),
        GeoPoint(30.91   , 79.78    ),
        GeoPoint(30.945  , 79.76    ),
        GeoPoint(30.94   , 79.735   ),
        GeoPoint(30.96   , 79.7	),
        GeoPoint(30.97   , 79.7	),
        GeoPoint(31.0682,79.7473),
        GeoPoint(30.7831,80.3181)
    )
    //巨哇、曲惹地区 2713483
    private val GUE_KAURIK  : Array<GeoPoint> = arrayOf(
        GeoPoint(31.9649,78.8227),
        GeoPoint(31.946,       78.766	),
        GeoPoint(31.95,       78.744	),
        GeoPoint(31.954,       78.736	),
        GeoPoint(31.962,       78.732	),
        GeoPoint(31.968,       78.734	),
        GeoPoint(31.978,       78.712	),
        GeoPoint(31.984,       78.71	),
        GeoPoint(31.99,       78.702	),
        GeoPoint(31.996,       78.68	),
        GeoPoint(31.994,       78.678	),
        GeoPoint(32.00,       78.67	),
        GeoPoint(32.00,       78.658	),
        GeoPoint(32.018,       78.626	),
        GeoPoint(32.016,       78.616	),
        GeoPoint(32.02,       78.614	),
        GeoPoint(32.02,       78.608	),
        GeoPoint(32.026,       78.604	),
        GeoPoint(32.024,       78.584	),
        GeoPoint(32.03,       78.574	),
        GeoPoint(32.046,       78.568	),
        GeoPoint(32.052,       78.578	),
        GeoPoint(32.072,       78.578	),
        GeoPoint(32.078,       78.566	),
        GeoPoint(32.094,       78.564	),
        GeoPoint(32.104,       78.55	),
        GeoPoint(32.104,       78.544	),
        GeoPoint(32.116,       78.524	),
        GeoPoint(32.122,       78.522	),
        GeoPoint(32.124,       78.516	),
        GeoPoint(32.148,       78.508	),
        GeoPoint(32.146,       78.498	),
        GeoPoint(32.138,       78.492	),
        GeoPoint(32.138,       78.486	),
        GeoPoint(32.134,       78.482	),
        GeoPoint(32.134,       78.472	),
        GeoPoint(32.128,       78.47	),
        GeoPoint(32.132,       78.456	),
        GeoPoint(32.146,       78.452	),
        GeoPoint(32.148,       78.446	),
        GeoPoint(32.154,       78.442	),
        GeoPoint(32.16,       78.448	),
        GeoPoint(32.176,       78.44	),
        GeoPoint(32.184,       78.444	),
        GeoPoint(32.194,       78.44	),
        GeoPoint(32.194,       78.434	),
        GeoPoint(32.204,       78.426	),
        GeoPoint(32.212,       78.428	),
        GeoPoint(32.216,       78.428	),
        GeoPoint(32.22,       78.444	),
        GeoPoint(32.232,       78.452	),
        GeoPoint(32.236,       78.47	),
        GeoPoint(32.238,       78.472	),
        GeoPoint(32.244,       78.47	),
        GeoPoint(32.25,       78.484	),
        GeoPoint(32.258,       78.482	),
        GeoPoint(32.26,       78.488	),
        GeoPoint(32.268,       78.484	),
        GeoPoint(32.272,       78.49	),
        GeoPoint(32.278,       78.492	),
        GeoPoint(32.278,       78.5	),
        GeoPoint(32.3057,78.6166)
    )
    //什布奇山口地区 2713485
    private val SHIPKI_LA  : Array<GeoPoint> = arrayOf(
        GeoPoint(31.8822,78.8226),
        GeoPoint(31.781,    78.7045	),
        GeoPoint(31.779,    78.705	),
        GeoPoint(31.7775,    78.7065	),
        GeoPoint(31.774,    78.706	),
        GeoPoint(31.7735,    78.702	),
        GeoPoint(31.7725,    78.7005	),
        GeoPoint(31.7735,    78.6955	),
        GeoPoint(31.7725,    78.6875	),
        GeoPoint(31.7760,    78.6831	),
        GeoPoint(31.7762,    78.6828	),
        GeoPoint(31.7763,    78.6827	),
        GeoPoint(31.779,    78.6795	),
        GeoPoint(31.778,    78.6755	),
        GeoPoint(31.779,    78.672	),
        GeoPoint(31.785,    78.6675	),
        GeoPoint(31.786,    78.665	),
        GeoPoint(31.7865,    78.658	),
        GeoPoint(31.793,    78.6515	),
        GeoPoint(31.819,    78.643	),
        GeoPoint(31.825,    78.6485	),
        GeoPoint(31.837,    78.65	),
        GeoPoint(31.8415,    78.655	),
        GeoPoint(31.8525,    78.662	),
        GeoPoint(31.8545,    78.6645	),
        GeoPoint(31.86,    78.6665	),
        GeoPoint(31.866,    78.674	),
        GeoPoint(31.869,    78.6795	),
        GeoPoint(31.87,    78.6885	),
        GeoPoint(31.8765,    78.6965	),
        GeoPoint(31.8805,    78.699	),
        GeoPoint(31.882,    78.701	),
        GeoPoint(31.883,    78.7055	),
        GeoPoint(31.8805,    78.7075	),
        GeoPoint(31.8775,    78.713	),
        GeoPoint(31.88,    78.7155	),
        GeoPoint(31.886,    78.719	),
        GeoPoint(31.8845,    78.735	)
    )
    //西巴里加斯 9823095
    private val WEST_PARIGAS : Array<GeoPoint> = arrayOf(
        	GeoPoint(32.554  , 79.278),
        	GeoPoint(32.556  , 79.275),
        	GeoPoint(32.556  , 79.27),
        	GeoPoint(32.56   , 79.265),
        	GeoPoint(32.563  , 79.265),
        	GeoPoint(32.566  , 79.271),
        	GeoPoint(32.57   , 79.272),
        	GeoPoint(32.573  , 79.277),
        	GeoPoint(32.578  , 79.277),
        	GeoPoint(32.579  , 79.28),
        	GeoPoint(32.584  , 79.282),
        	GeoPoint(32.589  , 79.29),
        	GeoPoint(32.591  , 79.29),
        	GeoPoint(32.592  , 79.288),
        	GeoPoint(32.597  , 79.288),
        	GeoPoint(32.601  , 79.295),
        	GeoPoint(32.601  , 79.302),
        	GeoPoint(32.603  , 79.305),
        	GeoPoint(32.606  , 79.302),
        	GeoPoint(32.612  , 79.301),
        	GeoPoint(32.616  , 79.297),
        	GeoPoint(32.619  , 79.297),
        	GeoPoint(32.628  , 79.289),
        	GeoPoint(32.632  , 79.29),
        	GeoPoint(32.635  , 79.294),
        	GeoPoint(32.64   , 79.294),
        	GeoPoint(32.645  , 79.287),
        	GeoPoint(32.648  , 79.285),
        	GeoPoint(32.651  , 79.286),
        	GeoPoint(32.652  , 79.282),
        	GeoPoint(32.657  , 79.277),
        	GeoPoint(32.663  , 79.278),
        	GeoPoint(32.665  , 79.275),
        	GeoPoint(32.673  , 79.271),
        	GeoPoint(32.674  , 79.269),
        	GeoPoint(32.684  , 79.267),
        	GeoPoint(32.689  , 79.275),
        	GeoPoint(32.689  , 79.278),
        	GeoPoint(32.693  , 79.28),
        	GeoPoint(32.695  , 79.283),
        	GeoPoint(32.702  , 79.284),
        	GeoPoint(32.704  , 79.279),
        	GeoPoint(32.711  , 79.279),
        	GeoPoint(32.712  , 79.281),
        	GeoPoint(32.715  , 79.281),
        	GeoPoint(32.715  , 79.287),
        	GeoPoint(32.718  , 79.290),
        	GeoPoint(32.718  , 79.290),
        	GeoPoint(32.718  , 79.290),
        	GeoPoint(32.722  , 79.294),
        	GeoPoint(32.729  , 79.295),
        	GeoPoint(32.731  , 79.289),
        	GeoPoint(32.734  , 79.287),
        	GeoPoint(32.74   , 79.289),
        	GeoPoint(32.741  , 79.286),
        	GeoPoint(32.747  , 79.284),
        	GeoPoint(32.75   , 79.281),
        	GeoPoint(32.76   , 79.282),
        	GeoPoint(32.764  , 79.276),
        	GeoPoint(32.77   , 79.274),
        	GeoPoint(32.777  , 79.274),
        	GeoPoint(32.781  , 79.27),
        	GeoPoint(32.781  , 79.268),
        	GeoPoint(32.783  , 79.267),
        	GeoPoint(32.788  , 79.258),
        	GeoPoint(32.79   , 79.246),
        	GeoPoint(32.789  , 79.242),
        	GeoPoint(32.786  , 79.24),
        	GeoPoint(32.783  , 79.234),
        	GeoPoint(32.786  , 79.222),
        	GeoPoint(32.792  , 79.225),
        	GeoPoint(32.795  , 79.222),
        	GeoPoint(32.798  , 79.222),
        	GeoPoint(32.803  , 79.225),
        	GeoPoint(32.81   , 79.222),
        	GeoPoint(32.815  , 79.229),
        	GeoPoint(32.821  , 79.228),
        	GeoPoint(32.823  , 79.231),
        	GeoPoint(32.828  , 79.224),
        	GeoPoint(32.833  , 79.224),
        	GeoPoint(32.837  , 79.228),
        	GeoPoint(32.843  , 79.229),
        	GeoPoint(32.846  , 79.234),
        	GeoPoint(32.848  , 79.234),
        	GeoPoint(32.853  , 79.228),
        	GeoPoint(32.856  , 79.227),
        	GeoPoint(32.863  , 79.227),
        	GeoPoint(32.866  , 79.228),
        	GeoPoint(32.868  , 79.231),
        	GeoPoint(32.874  , 79.23),
        	GeoPoint(32.876  , 79.233),
        	GeoPoint(32.881  , 79.233),
        	GeoPoint(32.885  , 79.231),
        	GeoPoint(32.887  , 79.226),
        	GeoPoint(32.893  , 79.224),
        	GeoPoint(32.896  , 79.228),
        	GeoPoint(32.902  , 79.229),
        	GeoPoint(32.903  , 79.231),
        	GeoPoint(32.914  , 79.23),
        	GeoPoint(32.945  , 79.251),
        	GeoPoint(32.948  , 79.249),
        	GeoPoint(32.964  , 79.221),
        	GeoPoint(32.972  , 79.224),
        GeoPoint(33.0155,79.2138),
        GeoPoint(33.2945,79.4237),
        GeoPoint(33.0181,79.4561),
        GeoPoint(32.4688,79.6273),
        GeoPoint(32.4729,79.2855)
    )
    //白玉地区
    private val BAIYU_WEST : Array<GeoPoint> = arrayOf(
        GeoPoint(27.985,91.19),
        GeoPoint(27.975,91.185),
        GeoPoint(27.973,91.167),
        GeoPoint(27.971,91.164),
        GeoPoint(27.957,91.159),
        GeoPoint(27.95,91.154),
        GeoPoint(27.945,91.138),
        GeoPoint(27.921,91.14),
        GeoPoint(27.905,91.145),
        GeoPoint(27.895,91.137),
        GeoPoint(27.883,91.135),
        GeoPoint(27.825,91.098),
        GeoPoint(27.813,91.084),
        GeoPoint(27.819,91.074),
        GeoPoint(27.83,91.04),
        GeoPoint(27.84,91.037),
        GeoPoint(27.861,90.986),
        GeoPoint(27.875,90.981),
        GeoPoint(27.889,90.981),
        GeoPoint(27.897,90.971),
        GeoPoint(27.903,90.959),
        GeoPoint(27.905,90.959),
        GeoPoint(27.911,90.967),
        GeoPoint(27.923,90.966),
        GeoPoint(27.937,90.977),
        GeoPoint(27.941,90.977),
        GeoPoint(27.946,90.969),
        GeoPoint(27.953,90.964),
        GeoPoint(27.954,90.955),
        GeoPoint(27.941,90.926),
        GeoPoint(27.948,90.911),
        GeoPoint(27.946,90.895),
        GeoPoint(27.96,90.866),
        GeoPoint(27.974,90.841),
        GeoPoint(27.994,90.824),
        GeoPoint(28.011,90.807),
        GeoPoint(28.013,90.8),
        GeoPoint(28.019,90.798),
        GeoPoint(28.029,90.8),
        GeoPoint(28.034,90.799),
        GeoPoint(28.037,90.803),
        GeoPoint(28.041,90.801),
        GeoPoint(28.045,90.796),
        GeoPoint(28.1137,91.1206)
    )
    private val BAIYU_EAST : Array<GeoPoint> = arrayOf(
        GeoPoint(28.061,91.31),
        GeoPoint(28.059,91.309),
        GeoPoint(28.056,91.31),
        GeoPoint(28.053,91.307),
        GeoPoint(28.048,91.313),
        GeoPoint(28.042,91.313),
        GeoPoint(28.039,91.309),
        GeoPoint(28.036,91.309),
        GeoPoint(28.028,91.303),
        GeoPoint(28.027,91.296),
        GeoPoint(28.03,91.293),
        GeoPoint(28.021,91.284),
        GeoPoint(28.017,91.284),
        GeoPoint(28.008,91.275),
        GeoPoint(28.005,91.277),
        GeoPoint(28.001,91.274),
        GeoPoint(27.996,91.275),
        GeoPoint(27.987,91.271),
        GeoPoint(27.986,91.264),
        GeoPoint(27.976,91.26),
        GeoPoint(27.97,91.25),
        GeoPoint(27.975,91.242),
        GeoPoint(27.975,91.234),
        GeoPoint(27.983,91.228),
        GeoPoint(27.978,91.217),
        GeoPoint(27.986,91.208),
        GeoPoint(27.988,91.208),
        GeoPoint(28.1356,91.1948)
    )
    //洞朗地区
    private val DOKLAM: Array<GeoPoint> = arrayOf(
        GeoPoint(27.268  , 88.930),
        GeoPoint(27.268  , 88.930),
        GeoPoint(27.268  , 88.930),
        GeoPoint(27.271  , 88.923),
        GeoPoint(27.269  , 88.913),
        GeoPoint(27.272  , 88.903),
        GeoPoint(27.274  , 88.903),
        GeoPoint(27.276  , 88.907),
        GeoPoint(27.283  , 88.908),
        GeoPoint(27.29   , 88.914),
        GeoPoint(27.299  , 88.911),
        GeoPoint(27.301  , 88.908),
        GeoPoint(27.305  , 88.908),
        GeoPoint(27.309  , 88.913),
        GeoPoint(27.316  , 88.915),
        GeoPoint(27.319  , 88.919),
        GeoPoint(27.327  , 88.919),
        GeoPoint(27.331  , 88.926),
        GeoPoint(27.332  , 88.925),
        GeoPoint(27.336  , 88.926),
        GeoPoint(27.336  , 88.929),
        GeoPoint(27.336  , 88.930),
        GeoPoint(27.336  , 88.930),
        GeoPoint(27.336  , 88.930),
        GeoPoint(27.336  , 88.930),
        GeoPoint(27.336  , 88.933),
        GeoPoint(27.339  , 88.937),
        GeoPoint(27.33   , 88.948),
        GeoPoint(27.322  , 88.952),
        GeoPoint(27.319  , 88.957),
        GeoPoint(27.311  , 88.962),
        GeoPoint(27.311  , 88.966),
        GeoPoint(27.307  , 88.97),
        GeoPoint(27.307  , 88.974),
        GeoPoint(27.312  , 88.976),
        GeoPoint(27.315  , 88.98),
        GeoPoint(27.317  , 88.98),
        GeoPoint(27.32   , 88.986),
        GeoPoint(27.32   , 88.991),
        GeoPoint(27.328  , 89.005),
        GeoPoint(27.326  , 89.006),
        GeoPoint(27.326  , 89.008),
        GeoPoint(27.319  , 89.008),
        GeoPoint(27.296  , 89.041),
        GeoPoint(27.297  , 89.043),
        GeoPoint(27.296  , 89.052),
        GeoPoint(27.292  , 89.056),
        GeoPoint(27.292  , 89.063),
        GeoPoint(27.29   , 89.067),
        GeoPoint(27.287  , 89.071),
        GeoPoint(27.275  , 89.069),
        GeoPoint(27.251  , 89.073),
        GeoPoint(27.239  , 89.066),
        GeoPoint(27.227  , 89.067),
        GeoPoint(27.217  , 89.059),
        GeoPoint(27.223  , 89.04),
        GeoPoint(27.224  , 89.028),
        GeoPoint(27.22   , 89.015),
        GeoPoint(27.219  , 89.006),
        GeoPoint(27.214  , 88.998),
        GeoPoint(27.212  , 88.991),
        GeoPoint(27.213  , 88.984),
        GeoPoint(27.223  , 88.97),
        GeoPoint(27.233  , 88.962),
        GeoPoint(27.235  , 88.958),
        GeoPoint(27.244  , 88.957),
        GeoPoint(27.246  , 88.952),
        GeoPoint(27.255  , 88.953),
        GeoPoint(27.26   , 88.95),
        GeoPoint(27.264  , 88.945)
    )
    //鲁林地区
    private val LULIN : Array<GeoPoint> = arrayOf(
 GeoPoint(    27.396  , 88.971),
 GeoPoint(    27.411  , 88.989),
 GeoPoint(    27.412  , 88.994),
 GeoPoint(    27.42  , 89.002),
 GeoPoint(    27.425  , 89.003),
 GeoPoint(    27.432  , 89.012),
 GeoPoint(    27.437  , 89.014),
 GeoPoint(    27.438  , 89.016),
 GeoPoint(    27.459  , 89.026),
 GeoPoint(   27.459  ,  89.03),
 GeoPoint(    27.463  , 89.038),
 GeoPoint(    27.467  , 89.041),
 GeoPoint(    27.468  , 89.044),
 GeoPoint(    27.472  , 89.046),
 GeoPoint(    27.471  , 89.059),
 GeoPoint(    27.468  , 89.066),
 GeoPoint(    27.464  , 89.068),
 GeoPoint(   27.464  ,  89.07),
 GeoPoint(    27.469  , 89.076),
 GeoPoint(    27.472  , 89.084),
 GeoPoint(    27.471  , 89.088),
 GeoPoint(   27.472  ,  89.09),
 GeoPoint(    27.47  , 89.093),
 GeoPoint(    27.472  , 89.093),
 GeoPoint(    27.474  , 89.096),
 GeoPoint(    27.467  , 89.102),
 GeoPoint(    27.469  , 89.105),
 GeoPoint(    27.465  , 89.114),
 GeoPoint(    27.462  , 89.112),
 GeoPoint(    27.46  , 89.113),
 GeoPoint(    27.456  , 89.112),
 GeoPoint(    27.452  , 89.116),
 GeoPoint(    27.452  , 89.118),
 GeoPoint(    27.443  , 89.118),
 GeoPoint(    27.442  , 89.124),
 GeoPoint(    27.443  , 89.136),
 GeoPoint(    27.437  , 89.137),
 GeoPoint(    27.433  , 89.145),
 GeoPoint(    27.428  , 89.146),
 GeoPoint(    27.429  , 89.152),
 GeoPoint(    27.427  , 89.155),
 GeoPoint(    27.431  , 89.159),
 GeoPoint(    27.426  , 89.161),
 GeoPoint(    27.421  , 89.158),
 GeoPoint(    27.417  , 89.158),
 GeoPoint(    27.414  , 89.163),
 GeoPoint(    27.412  , 89.163),
 GeoPoint(    27.409  , 89.166),
 GeoPoint(    27.409  , 89.169),
 GeoPoint(    27.407  , 89.169),
 GeoPoint(    27.405  , 89.166),
 GeoPoint(    27.399  , 89.169),
 GeoPoint(    27.395  , 89.168),
 GeoPoint(    27.39  , 89.172),
 GeoPoint(    27.389  , 89.175),
 GeoPoint(    27.376  , 89.181),
 GeoPoint(    27.37  , 89.173),
 GeoPoint(    27.367  , 89.171),
 GeoPoint(    27.363  , 89.172),
 GeoPoint(    27.358  , 89.167),
 GeoPoint(    27.358  , 89.162),
 GeoPoint(    27.355  , 89.161),
 GeoPoint(    27.341  , 89.163),
 GeoPoint(    27.34  , 89.161),
 GeoPoint(    27.34  , 89.162),
 GeoPoint(    27.336  , 89.161),
 GeoPoint(    27.329  , 89.154),
 GeoPoint(    27.325  , 89.155),
 GeoPoint(    27.317  , 89.153),
 GeoPoint(    27.316  , 89.151),
 GeoPoint(    27.318  , 89.144),
 GeoPoint(    27.316  , 89.134),
 GeoPoint(    27.314  , 89.132),
 GeoPoint(    27.314  , 89.128),
 GeoPoint(    27.311  , 89.122),
 GeoPoint(    27.31  , 89.112),
 GeoPoint(  27.298  ,   89.1),
 GeoPoint(    27.292  , 89.085),
 GeoPoint(    27.284  , 89.081),
 GeoPoint(    27.282  , 89.072),
 GeoPoint(    27.284  , 89.068),
 GeoPoint(    27.286  , 89.069),
 GeoPoint(    27.289  , 89.065),
 GeoPoint(    27.29  , 89.056),
 GeoPoint(    27.293  , 89.053),
 GeoPoint(    27.295  , 89.047),
 GeoPoint(    27.294  , 89.043),
 GeoPoint(    27.295  , 89.039),
 GeoPoint(    27.317  , 89.006),
 GeoPoint(    27.324  , 89.006),
 GeoPoint(    27.329  , 89.001),
 GeoPoint(    27.336  , 89.001),
 GeoPoint(    27.34  , 88.997),
 GeoPoint(    27.342  , 88.998),
 GeoPoint(    27.345  , 88.996),
 GeoPoint(    27.347  , 88.988),
 GeoPoint(    27.35  , 88.988),
 GeoPoint(    27.351  , 88.983),
 GeoPoint(   27.354  ,  88.98),
 GeoPoint(    27.354  , 88.977),
 GeoPoint(    27.356  , 88.976),
 GeoPoint(    27.364  , 88.979),
 GeoPoint(    27.371  , 88.976),
 GeoPoint(    27.373  , 88.977),
 GeoPoint(    27.374  , 88.975),
 GeoPoint(    27.379  , 88.976),
 GeoPoint(    27.382  , 88.974),
 GeoPoint(    27.385  , 88.975),
 GeoPoint(   27.394  ,  88.97),
 GeoPoint(    27.396  , 88.971)
    )
    //查马普地区 3964595
    private val CHAMPA : Array<GeoPoint> = arrayOf(
        GeoPoint(27.413,88.95),
        GeoPoint(27.417,88.955),
        GeoPoint(27.418,88.961),
        GeoPoint(27.424,88.964),
        GeoPoint(27.426,88.963),
        GeoPoint(27.427,88.96),
        GeoPoint(27.431,88.959),
        GeoPoint(27.435,88.955),
        GeoPoint(27.444,88.958),
        GeoPoint(27.449,88.958),
        GeoPoint(27.455,88.961),
        GeoPoint(27.46,88.966),
        GeoPoint(27.475,88.964),
        GeoPoint(27.478,88.97),
        GeoPoint(27.483,88.971),
        GeoPoint(27.483,88.974),
        GeoPoint(27.489,88.978),
        GeoPoint(27.493,88.988),
        GeoPoint(27.5,88.991),
        GeoPoint(27.5,88.993),
        GeoPoint(27.502,88.994),
        GeoPoint(27.499,89.005),
        GeoPoint(27.503,89.007),
        GeoPoint(27.505,89.013),
        GeoPoint(27.509,89.014),
        GeoPoint(27.509,89.018),
        GeoPoint(27.511,89.02),
        GeoPoint(27.511,89.028),
        GeoPoint(27.509,89.034),
        GeoPoint(27.513,89.037),
        GeoPoint(27.515,89.041),
        GeoPoint(27.514,89.045),
        GeoPoint(27.518,89.051),
        GeoPoint(27.518,89.054),
        GeoPoint(27.513,89.061),
        GeoPoint(27.518,89.072),
        GeoPoint(27.52,89.083),
        GeoPoint(27.525,89.087),
        GeoPoint(27.523,89.094),
        GeoPoint(27.525,89.104),
        GeoPoint(27.52,89.111),
        GeoPoint(27.516,89.111),
        GeoPoint(27.513,89.108),
        GeoPoint(27.51,89.109),
        GeoPoint(27.509,89.107),
        GeoPoint(27.505,89.106),
        GeoPoint(27.502,89.098),
        GeoPoint(27.499,89.097),
        GeoPoint(27.494,89.1),
        GeoPoint(27.488,89.101),
        GeoPoint(27.486,89.098),
        GeoPoint(27.475,89.098),
        GeoPoint(27.469,89.094),
        GeoPoint(27.468,89.092),
        GeoPoint(27.47,89.089),
        GeoPoint(27.47,89.084),
        GeoPoint(27.464,89.072),
        GeoPoint(27.462,89.071),
        GeoPoint(27.462,89.067),
        GeoPoint(27.465,89.066),
        GeoPoint(27.469,89.058),
        GeoPoint(27.47,89.047),
        GeoPoint(27.467,89.046),
        GeoPoint(27.466,89.043),
        GeoPoint(27.461,89.039),
        GeoPoint(27.457,89.027),
        GeoPoint(27.442,89.021),
        GeoPoint(27.434,89.014),
        GeoPoint(27.431,89.014),
        GeoPoint(27.424,89.005),
        GeoPoint(27.422,89.005),
        GeoPoint(27.413,88.998),
        GeoPoint(27.409,88.99),
        GeoPoint(27.394,88.973),
        GeoPoint(27.394,88.97),
        GeoPoint(27.398,88.969),
        GeoPoint(27.401,88.963),
        GeoPoint(27.405,88.963),
        GeoPoint(27.406,88.958),
        GeoPoint(27.409,88.956),
        GeoPoint(27.411,88.95),
        GeoPoint(27.413,88.95)
    )
    //基伍地区 3964660
    private val KIWU : Array<GeoPoint> = arrayOf(
        GeoPoint(27.526, 88.975),
        GeoPoint(27.526, 88.975),
        GeoPoint(27.527, 88.975),
        GeoPoint(27.527, 88.975),
        GeoPoint(27.527, 88.975),
        GeoPoint(27.527, 88.975),
        GeoPoint(27.529, 88.976),
        GeoPoint(27.531, 88.978),
        GeoPoint(27.531, 88.984),
        GeoPoint(27.537, 88.99),
        GeoPoint(27.538, 88.994),
        GeoPoint(27.547, 88.998),
        GeoPoint(27.55,  89.005),
        GeoPoint(27.555, 89.003),
        GeoPoint(27.555, 88.998),
        GeoPoint(27.558, 88.998),
        GeoPoint(27.56,  89.002),
        GeoPoint(27.568, 89.005),
        GeoPoint(27.576, 89.015),
        GeoPoint(27.578, 89.032),
        GeoPoint(27.586, 89.037),
        GeoPoint(27.59,  89.036),
        GeoPoint(27.591, 89.039),
        GeoPoint(27.597, 89.039),
        GeoPoint(27.609, 89.05),
        GeoPoint(27.61,  89.058),
        GeoPoint(27.615, 89.06),
        GeoPoint(27.616, 89.067),
        GeoPoint(27.62,  89.07),
        GeoPoint(27.62,  89.075),
        GeoPoint(27.623, 89.078),
        GeoPoint(27.622, 89.081),
        GeoPoint(27.624, 89.084),
        GeoPoint(27.624, 89.088),
        GeoPoint(27.626, 89.088),
        GeoPoint(27.626, 89.093),
        GeoPoint(27.628, 89.096),
        GeoPoint(27.623, 89.1),
        GeoPoint(27.625, 89.109),
        GeoPoint(27.62,  89.111),
        GeoPoint(27.62,  89.119),
        GeoPoint(27.617, 89.122),
        GeoPoint(27.618, 89.124),
        GeoPoint(27.616, 89.126),
        GeoPoint(27.616, 89.129),
        GeoPoint(27.614, 89.13),
        GeoPoint(27.613, 89.133),
        GeoPoint(27.609, 89.135),
        GeoPoint(27.608, 89.141),
        GeoPoint(27.604, 89.144),
        GeoPoint(27.604, 89.146),
        GeoPoint(27.6,   89.148),
        GeoPoint(27.599, 89.151),
        GeoPoint(27.595, 89.15),
        GeoPoint(27.594, 89.153),
        GeoPoint(27.587, 89.152),
        GeoPoint(27.582, 89.163),
        GeoPoint(27.576, 89.163),
        GeoPoint(27.573, 89.162),
        GeoPoint(27.572, 89.16),
        GeoPoint(27.567, 89.16),
        GeoPoint(27.564, 89.155),
        GeoPoint(27.56,  89.155),
        GeoPoint(27.557, 89.153),
        GeoPoint(27.553, 89.143),
        GeoPoint(27.553, 89.137),
        GeoPoint(27.546, 89.134),
        GeoPoint(27.542, 89.128),
        GeoPoint(27.541, 89.119),
        GeoPoint(27.543, 89.115),
        GeoPoint(27.535, 89.107),
        GeoPoint(27.532, 89.109),
        GeoPoint(27.53,  89.106),
        GeoPoint(27.523, 89.104),
        GeoPoint(27.522, 89.102),
        GeoPoint(27.521, 89.094),
        GeoPoint(27.523, 89.088),
        GeoPoint(27.519, 89.085),
        GeoPoint(27.516, 89.077),
        GeoPoint(27.516, 89.072),
        GeoPoint(27.511, 89.062),
        GeoPoint(27.511, 89.059),
        GeoPoint(27.516, 89.054),
        GeoPoint(27.516, 89.052),
        GeoPoint(27.513, 89.049),
        GeoPoint(27.512, 89.039),
        GeoPoint(27.51,  89.039),
        GeoPoint(27.507, 89.035),
        GeoPoint(27.509, 89.028),
        GeoPoint(27.508, 89.025),
        GeoPoint(27.509, 89.02),
        GeoPoint(27.507, 89.019),
        GeoPoint(27.506, 89.015),
        GeoPoint(27.502, 89.014),
        GeoPoint(27.502, 89.009),
        GeoPoint(27.496, 89.006),
        GeoPoint(27.5,   88.994),
        GeoPoint(27.491, 88.988),
        GeoPoint(27.491, 88.986),
        GeoPoint(27.51,  88.976),
        GeoPoint(27.514, 88.97),
        GeoPoint(27.516, 88.971),
        GeoPoint(27.518, 88.971),
        GeoPoint(27.522, 88.975),
        GeoPoint(27.526, 88.975)
    )
    //九段线
    private val JIU_DUAN_XIAN : Array<GeoPoint> = arrayOf(
        GeoPoint(16.222, 109.307),
        GeoPoint(15.154, 109.856),
        GeoPoint(12.209, 110.330),
        GeoPoint(11.200, 110.433),
        GeoPoint(7.0093, 108.585),
        GeoPoint(5.9395, 108.662),
        GeoPoint(3.3445, 112.144),
        GeoPoint(3.7189, 113.029),
        GeoPoint(7.1308, 115.721),
        GeoPoint(7.9639, 116.424),
        GeoPoint(10.869, 118.720),
        GeoPoint(11.946, 119.160),
        GeoPoint(14.805, 119.237),
        GeoPoint(15.975, 119.237),
        GeoPoint(17.987, 119.649),
        GeoPoint(18.956, 120.182),
        GeoPoint(20.801, 121.353),
        GeoPoint(21.667, 122.090),
        GeoPoint(23.440, 122.683),
        GeoPoint(24.609, 122.985)
    )

    fun getLocalizedAddress(): String{
        val geoPoint = GeoPoint(address.latitude.toDouble(), address.longitude.toDouble())
        if (address.ranks.getString("country_code") == "cn"){
            return address.display_name
        } else if (address.ranks.getString("country_code") == "tw"){
            if (Rectangle(122.380, 117.046, 21.409, 26.543)
                    .isIn(geoPoint)){  //in taiwan or fujian.
                if (address.ranks.getString("state") == "福建省" || address.ranks.getString("state") == "臺灣省" || address.ranks.getString("state") == "台灣省" ||address.ranks.getString("state") == "台湾省" )
                    return address.display_name.split(address.ranks.getString("postcode"))[0] + "中国"
                else
                    return address.display_name.split(address.ranks.getString("postcode"))[0] + "台湾省, 中国"
            } else if (address.latitude > 15) { //东沙
                return southChinaSea(false)
            } else //南沙
                return southChinaSea(true)
        } else if (address.ranks.getString("country_code") == "in"){
            if ((address.longitude > 91.400) && (PnPoly(
                    SOUTH_TIBET
                ).isIn(geoPoint))){ //need more work
                    if (address.display_name.contains("达旺") || address.display_name.contains("達旺") || address.display_name.contains("Tawang"))
                        return "达旺, 错那县, 山南市, 西藏自治区, 中国"
                    else
                        return "西藏自治区, 中国"
            } else if (Rectangle(
                    79.4816,
                    79.2044,
                    32.5072,
                    32.9890
                ).isIn(geoPoint) && PnPoly(WEST_PARIGAS).isIn(geoPoint))
                return "巴里加斯, 扎西岗乡, 噶尔县, 阿里地区, 西藏自治区, 中国"
            else if (Rectangle(
                    78.7713,
                    78.5550,
                    32.5732,
                    32.7281
                ).isIn(geoPoint)){
                if (PnPoly(CHUMAR).isIn(geoPoint))
                    return "楚木惹地区, 楚鲁松杰乡, 札达县, 阿里地区, 西藏自治区, 中国"
                else if (PnPoly(SERUURRI).isIn(geoPoint))
                    return "斯诺乌山地区, 楚鲁松杰乡, 札达县, 阿里地区, 西藏自治区, 中国"
                else
                    return address.display_name
            } else if (Rectangle(
                    78.7997,
                    78.4040,
                    31.9212,
                    32.2990
                ).isIn(geoPoint) && PnPoly(GUE_KAURIK).isIn(geoPoint))
                return "巨哇、曲惹地区, 楚鲁松杰乡, 札达县, 阿里地区, 西藏自治区, 中国"
            else if (Rectangle(
                    78.7780,
                    78.6267,
                    31.7670,
                    31.9016
                ).isIn(geoPoint) && PnPoly(SHIPKI_LA).isIn(geoPoint))
                return "什布奇山口地区, 什布奇村, 底雅乡, 札达县, 阿里地区, 西藏自治区, 中国"
            else if (Rectangle(
                    79.4376,
                    78.8660,
                    30.9346,
                    31.4772
                ).isIn(geoPoint) && PnPoly(
                    SANG_TSHONG_SA_PULAM_SUMDA
                ).isIn(geoPoint))
                return "桑、葱莎、波林三多地区, 札达县, 阿里地区, 西藏自治区, 中国"
            else if (Rectangle(
                    80.3107,
                    79.6595,
                    30.5100,
                    31.0574
                ).isIn(geoPoint) && PnPoly(
                    WUJE_RAKTROM_LAPTHAL
                ).isIn(geoPoint))
                return "乌热、然冲、拉不底地区, 达巴乡, 札达县, 阿里地区, 西藏自治区, 中国"
            else
                return address.display_name
        } else if (address.ranks.getString("country_code") == "bt"){
            if (Rectangle(
                    89.1893,
                    88.7499,
                    27.2046,
                    27.6271
                ).isIn(geoPoint)){
                if (PnPoly(KIWU).isIn(geoPoint))
                    return localityNameGet("基伍", "基伍地区, 上亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国", "不丹")
                else if (PnPoly(CHAMPA).isIn(geoPoint))
                    return localityNameGet("查马浦", "查马浦地区, 帮噶曲登, 上亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国", "不丹")
                else if (PnPoly(LULIN).isIn(geoPoint))
                    return localityNameGet("鲁林", "鲁林地区, 仁青岗村, 下亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国", "不丹")
                else if (PnPoly(DOKLAM).isIn(geoPoint))
                    return localityNameGet("洞朗地区", "洞朗地区, 下亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国", "不丹")
                else if (Rectangle(
                        89.0298,
                        88.7527,
                        27.2886,
                        27.5363
                    ).isIn(geoPoint))
                    return localityNameGet("下亚东", "下亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国", "不丹")
                else
                    return address.display_name
            } else if (Rectangle(
                    91.3442,
                    90.7698,
                    27.7934,
                    28.1026
                ).isIn(geoPoint) && (PnPoly(BAIYU_EAST)
                    .isIn(geoPoint) || PnPoly(BAIYU_WEST).isIn(geoPoint)))
                    return "白玉地区, 洛扎县, 山南市, 西藏自治区, 中国"
             else
                return address.display_name
        } else if ( address.ranks.getString("country_code") == "ph" ||
                    address.ranks.getString("country_code") == "vn" ||
                    address.ranks.getString("country_code") == "my"     ){
            if (PnPoly(JIU_DUAN_XIAN).isIn(geoPoint))
                return southChinaSea(true)
            else
                return address.display_name
        } else if (Rectangle(
                124.6270,
                123.2129,
                25.5882,
                25.9972
            ).isIn(geoPoint))
            return "钓鱼岛列岛, 大溪里, 头城镇, 宜兰县, 台湾省, 中国"
        else
            return address.display_name
    }

    fun southChinaSea(bool : Boolean) : String{
        var string : String
            if (bool)
                string = "三沙市, 海南省, 中国"  //true 西中南沙
            else
                string = "东沙群岛, 碣石镇, 陆丰市, 汕尾市, 广东省, 中国"  // false 东沙
        if(address.display_name.contains("岛"))
            return address.display_name.split("岛")[0] +  "岛, " + string
        else if (address.display_name.contains("島"))
            return address.display_name.split("島")[0] +  "岛, " + string
        else if (address.display_name.contains("礁,"))
            return address.display_name.split("礁,")[0] + "礁, " + string
        else if (address.display_name.contains("洲,"))
            return address.display_name.split("洲,")[0] + "洲, " + string
        else if (address.display_name.contains("滩,"))
            return address.display_name.split("滩,")[0] + "滩, " + string
        else if (address.display_name.contains("中興里") && address.display_name.split(",")[0] != "中興里")
            return address.display_name.split(",")[0] + ", " + string
        else
            return string
    }

    fun localityNameGet(smallPlaceName : String, administration: String, original : String) : String{
        if (address.display_name.contains(smallPlaceName)){
            if (address.display_name.indexOf(smallPlaceName) != 0) //if contains the disputed area name
                return address.display_name.split(smallPlaceName)[0] + administration
            else{ //not contains the disputed area name

                //if contains chinese character
                val strList = address.display_name.split(",")
                val regEx = "[\\u4e00-\\u9fa5]"
                val p = Pattern.compile(regEx)
                var m : Matcher
                var count : Int
                var index : Int = 0
                for((i: Int, str: String) in strList.withIndex()){
                    m =  p.matcher(str)
                    count = 0
                    while (m.find()) { //m.matches()全部匹配为true
                        //m.groupCount()用于获取正则模式中子模式匹配的组，即只有正则中含有（）分组的情况下才有用
                        for (j in 0 .. m.groupCount()) {
                            count++
                        }
                    }
                    if (count == 0){
                        index = i
                        break
                    }
                }

                //把第一个完全没有汉字的字段的前面的部分拉出来。
                if (index == 0)
                    return administration
                else if (index == (strList.size -1))
                    return address.display_name.split(original)[0] + administration
                else
                    return address.display_name.split(strList[index+1])[0] + administration
            }
        } else
            return administration
    }
}