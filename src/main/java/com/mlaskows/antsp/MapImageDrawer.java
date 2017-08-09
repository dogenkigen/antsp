package com.mlaskows.antsp;

public class MapImageDrawer {


    private static final String BASE =
            "/Users/mlaskows/IdeaProjects/antlib/anttsplib/src/main/resources/";
    private static final String FILE_NAME = "usa13509.tsp";

    private static final int FACTOR = 100;
/*
    public static void drwaMapImage() {
        try (Stream<String> stream = Files.lines(Paths.get(BASE + FILE_NAME))) {
            List<City> cities = stream
                    .map(s -> getSplited(s))
                    .map(sa -> new City(sa))
                    .collect(Collectors.toList());

            int width = (int) cities.stream().mapToDouble(city -> city.getX())
                    .max().getAsDouble() / FACTOR;

            int height = (int) cities.stream().mapToDouble(city -> city.getY())
                    .max()
                    .getAsDouble() / FACTOR;


            BufferedImage bi = new BufferedImage(width, height, BufferedImage
                    .TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();

            ig2.setColor(Color.BLACK);
            ig2.setBackground(Color.WHITE);

            List<City> smaller = cities.stream()
                    .map(city -> new City(city.getId(), city.getY() / FACTOR, city
                            .getX() / FACTOR)
                    ).collect(Collectors.toList());


            for (City c : smaller) {
                int d = 1;
                *//*if (c.getY().intValue() > height / 2 || c.getX().intValue() >
                        width / 2) {
                    d = 10;
                }*//*
                ig2.draw3DRect(c.getX().intValue(), c.getY().intValue(), d, d,
                        true);
            }


            ImageIO.write(bi, "PNG", new File(FILE_NAME + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    private static String[] getSplited(String s) {
        return s.trim().split("\\s+");
    }
}