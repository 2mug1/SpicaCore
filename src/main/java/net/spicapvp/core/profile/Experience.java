package net.spicapvp.core.profile;

import lombok.Getter;
import lombok.Setter;
import net.spicapvp.core.util.Style;
import org.bukkit.Sound;

import java.util.HashMap;
import java.util.Map;

public class Experience {

    private static final int MAX_LEVEL = 300;

    /* LEVEL : EXP で格納 */
    private static final Map LEVELING_MAP = new HashMap<Integer, Integer>() {
        {
            put(1, 200);
            put(2, 300);
            put(3, 400);
            put(4, 500);
            put(5, 600);
            put(6, 700);
            put(7, 800);
            put(8, 900);
            put(9, 1000);
            put(10, 1100);

            put(11, 1300);
            put(12, 1400);
            put(13, 1500);
            put(14, 1600);
            put(15, 1700);
            put(16, 1800);
            put(17, 1900);
            put(18, 2000);
            put(19, 2100);
            put(20, 2200);

            put(21, 2300);
            put(22, 2400);
            put(23, 2500);
            put(24, 2600);
            put(25, 2700);
            put(26, 2800);
            put(27, 2900);
            put(28, 3000);
            put(29, 3100);
            put(30, 3200);

            put(31, 3300);
            put(32, 3400);
            put(33, 3500);
            put(34, 3600);
            put(35, 3700);
            put(36, 3800);
            put(37, 3900);
            put(38, 4000);
            put(39, 4100);
            put(40, 4200);

            put(41, 4300);
            put(42, 4400);
            put(43, 4500);
            put(44, 4600);
            put(45, 4700);
            put(46, 4800);
            put(47, 4900);
            put(48, 5000);
            put(49, 5100);
            put(50, 5200);

            put(51, 4300);
            put(52, 4400);
            put(53, 4500);
            put(54, 4600);
            put(55, 4700);
            put(56, 4800);
            put(57, 4900);
            put(58, 5000);
            put(59, 5100);
            put(60, 5200);

            put(61, 5300);
            put(62, 5400);
            put(63, 5500);
            put(64, 5600);
            put(65, 5700);
            put(66, 5800);
            put(67, 5900);
            put(68, 6000);
            put(69, 6100);
            put(70, 6200);

            put(71, 6300);
            put(72, 6400);
            put(73, 6500);
            put(74, 6600);
            put(75, 6700);
            put(76, 6800);
            put(77, 6900);
            put(78, 7000);
            put(79, 7100);
            put(80, 7200);

            put(81, 7300);
            put(82, 7400);
            put(83, 7500);
            put(84, 7600);
            put(85, 7700);
            put(86, 7800);
            put(87, 7900);
            put(88, 8000);
            put(89, 8100);
            put(90, 8500);

            put(91, 9000);
            put(92, 9100);
            put(93, 9200);
            put(94, 9300);
            put(95, 9400);
            put(96, 10000);
            put(97, 13000);
            put(98, 15000);
            put(99, 16000);
            put(100, 17000);

            put(101, 9300);
            put(102, 9400);
            put(103, 9500);
            put(104, 9600);
            put(105, 9700);
            put(106, 9800);
            put(107, 9900);
            put(108, 10000);
            put(109, 10100);
            put(110, 10200);

            put(111, 10300);
            put(112, 10400);
            put(113, 10500);
            put(114, 10600);
            put(115, 10700);
            put(116, 10800);
            put(117, 10900);
            put(118, 11000);
            put(119, 11100);
            put(120, 11200);

            put(121, 11300);
            put(122, 11400);
            put(123, 11500);
            put(124, 11600);
            put(125, 11700);
            put(126, 11800);
            put(127, 11900);
            put(128, 12000);
            put(129, 12100);
            put(130, 12200);

            put(131, 12300);
            put(132, 12400);
            put(133, 12500);
            put(134, 12600);
            put(135, 12700);
            put(136, 12800);
            put(137, 12900);
            put(138, 13000);
            put(139, 13100);
            put(140, 13200);

            put(141, 13300);
            put(142, 13400);
            put(143, 13500);
            put(144, 13600);
            put(145, 13700);
            put(146, 13800);
            put(147, 13900);
            put(148, 14000);
            put(149, 14100);
            put(150, 14200);

            put(151, 14300);
            put(152, 14400);
            put(153, 14500);
            put(154, 14600);
            put(155, 14700);
            put(156, 14800);
            put(157, 14900);
            put(158, 15000);
            put(159, 15100);
            put(160, 15200);

            put(161, 15300);
            put(162, 15400);
            put(163, 15500);
            put(164, 15600);
            put(165, 15700);
            put(166, 15800);
            put(167, 15900);
            put(168, 16000);
            put(169, 16100);
            put(170, 16200);

            put(171, 16300);
            put(172, 16400);
            put(173, 16500);
            put(174, 16600);
            put(175, 16700);
            put(176, 16800);
            put(177, 16900);
            put(178, 17000);
            put(179, 17100);
            put(180, 17200);

            put(181, 17300);
            put(182, 17400);
            put(183, 17500);
            put(184, 17600);
            put(185, 17700);
            put(186, 17800);
            put(187, 17900);
            put(188, 18000);
            put(189, 18100);
            put(190, 18200);

            put(191, 18300);
            put(192, 18400);
            put(193, 18500);
            put(194, 18600);
            put(195, 18700);
            put(196, 18800);
            put(197, 18900);
            put(198, 19000);
            put(199, 19100);
            put(200, 19200);

            put(201, 19300);
            put(202, 19400);
            put(203, 19500);
            put(204, 19600);
            put(205, 19700);
            put(206, 19800);
            put(207, 19900);
            put(208, 20000);
            put(209, 20100);
            put(210, 20200);

            put(211, 20300);
            put(212, 20400);
            put(213, 20500);
            put(214, 20600);
            put(215, 20700);
            put(216, 20800);
            put(217, 20900);
            put(218, 21000);
            put(219, 21100);
            put(220, 21200);

            put(221, 21300);
            put(222, 21400);
            put(223, 21500);
            put(224, 21600);
            put(225, 21700);
            put(226, 21800);
            put(227, 21900);
            put(228, 22000);
            put(229, 22100);
            put(230, 22200);

            put(231, 22300);
            put(232, 22400);
            put(233, 22500);
            put(234, 22600);
            put(235, 22700);
            put(236, 22800);
            put(237, 22900);
            put(238, 23000);
            put(239, 23100);
            put(240, 23200);

            put(241, 23300);
            put(242, 23400);
            put(243, 23500);
            put(244, 23600);
            put(245, 23700);
            put(246, 23800);
            put(247, 23900);
            put(248, 24000);
            put(249, 24100);
            put(250, 24200);

            put(251, 24300);
            put(252, 24400);
            put(253, 24500);
            put(254, 24600);
            put(255, 24700);
            put(256, 24800);
            put(257, 24900);
            put(258, 25000);
            put(259, 25100);
            put(260, 25200);

            put(261, 25300);
            put(262, 25400);
            put(263, 25500);
            put(264, 25600);
            put(265, 25700);
            put(266, 25800);
            put(267, 25900);
            put(268, 26000);
            put(269, 26100);
            put(270, 26200);

            put(271, 26300);
            put(272, 26400);
            put(273, 26500);
            put(274, 26600);
            put(275, 26700);
            put(276, 26800);
            put(277, 26900);
            put(278, 27000);
            put(279, 27100);
            put(280, 27200);

            put(281, 27300);
            put(282, 27400);
            put(283, 27500);
            put(284, 27600);
            put(285, 27700);
            put(286, 27800);
            put(287, 27900);
            put(288, 28000);
            put(289, 28100);
            put(290, 28200);

            put(291, 28300);
            put(292, 28400);
            put(293, 28500);
            put(294, 28600);
            put(295, 28700);
            put(296, 28800);
            put(297, 28900);
            put(298, 29000);
            put(299, 29100);
            put(300, 29200);
        }
    };

    private Profile profile;

    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    private int exp;
    @Getter
    @Setter
    private int totalExp;

    public Experience(Profile profile) {
        this.profile = profile;
    }

    public boolean isMax() {
        return level == MAX_LEVEL;
    }

    public int getNextLevel() {
        if (isMax()) {
            return -1;
        }
        return level + 1;
    }

    public int needNextLevelExp() {
        return (int) LEVELING_MAP.get(getNextLevel());
    }

    private void updateLevel(int to) {
        this.level = to;
        profile.save();
    }

    public void addExp(int amount) {
        setExp(exp + amount);
        setTotalExp(totalExp + amount);

        profile.getPlayer().sendMessage(Style.YELLOW + " You've gained " + Style.AQUA + "+" + amount + " exp");

        boolean levelup = false;

        final int before = level;

        int remaining = exp - needNextLevelExp();
        if (remaining >= 0) {
            setExp(remaining);
            updateLevel(getNextLevel());
            levelup = true;
        }

        while (needNextLevelExp() <= exp) {
            remaining = exp - needNextLevelExp();
            if(remaining >= 0) {
                level++;
                setExp(remaining);
                levelup = true;
            }
        }

        profile.save();

        if(levelup){
            profile.getPlayer().sendMessage(Style.YELLOW + Style.BOLD + " LEVEL UP! " + Style.AQUA + before + "→" + level);
            profile.getPlayer().playSound(profile.getPlayer().getLocation(), Sound.LEVEL_UP, 0.5F, 1);
        }
    }
}