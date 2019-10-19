package me.jan.ardashops.old;

public class Test {

    @org.junit.Test
    public void justTest(){
        /**Map<String, Integer> enchantmentIntegerMap = new HashMap<String, Integer>();
        Map<Enchantment, Integer> e = new HashMap<Enchantment, Integer>();
        enchantmentIntegerMap.put("DURABILITY", 2);
        enchantmentIntegerMap.put("SILK_TOUCH", 200);
        enchantmentIntegerMap.put("KNOCKBACK", 14);
        for (Map.Entry<String, Integer> entrySet:
                enchantmentIntegerMap.entrySet()) {
            e.put(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(entrySet.getKey().toLowerCase())), entrySet.getValue());

            System.out.println(entrySet.getKey() + "===" + entrySet.getValue());
        }
        System.out.println(e.toString());*/
    }

    @org.junit.Test
    public void loadingFilesTest() {
        /**List<Shop> shops = new ArrayList<Shop>();
        File filepath = new File("C:\\Users\\PC\\Desktop\\te");
        double step = 100.0/filepath.listFiles().length;
        double prog = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            for (File f :
                    filepath.listFiles()) {
                Shop shop = mapper.readValue(f, Shop.class);
                shops.add(shop);
                System.out.print("\r");
                System.out.print("Loading Shops... " + df.format(prog += step) + "%");
            }
            System.out.println();
            System.out.println("Loading Shops done!");
            System.out.println(ReflectionToStringBuilder.toString(shops, ToStringStyle.MULTI_LINE_STYLE));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
