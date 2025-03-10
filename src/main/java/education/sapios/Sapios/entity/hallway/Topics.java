package education.sapios.Sapios.entity.hallway;

public enum Topics {
    ONE(
            "C.1 - Francia, una revolución social",
            """
                    La discusión sobre qué originó la Revolución francesa es antigua e involucra explicaciones que se mueven en la corta y en la mediana duración. Es decir, algunas investigaciones explican su origen a partir de lo que ocurrió pocos años antes, mientras que otras creen que este debe buscarse al investigar las transformaciones que ocurren en Francia desde varias décadas antes de la revolución.
                    
                    Algunos autores ponen énfasis en el impacto de las ideas ilustradas en Francia, el auge de la burguesía francesa y su resentimiento hacia el absolutismo que una vez fuera pregonado por el rey Luis XIV (1638-1715) con una recordada frase que la tradición le atribuyó: L'État, c'est moi (El Estado soy yo). Otros autores subrayan el papel de elementos, tales como la mala administración estatal, que habían llevado adelante las coronas francesas, al promover una inmanejable deuda cuyo peso recaía en la mayoría de la población a través de impuestos. Asimismo, están quienes privilegian las causas más próximas temporalmente al estallido de la revolución, que presentan la escasez de alimentos y la subida estrepitosa en los precios de los bienes de consumo popular (como el pan) como el detonante de la crisis (Rudé, 1988; 1-24).
                    
                    Todos estos elementos se fundieron en una chispa que prendió fuego a una mecha social que, como ninguna otra en Europa, estaba lista para producir una hecatombe sociopolítica, Y no hay duda de que quien lideró este terremoto social fue esa clase media en ascenso: la burguesía (Hobsbawm, 1990:1-32).
                    La Revolución francesa, aunque no inauguró esa lucha contra la monarquía que ya se había dado en Inglaterra en el siglo XVII y que se expresó también con la revolución de independencia de los Estados Unidos, sí llevó a los límites de su implementación las ideas ilustradas. Durante diez años, de 1789 a 1799, Francia experimentó el vaivén del poder de un grupo revolucionario a otro y la activa participación ciudadana en cada discusión y decisión, así como la toma de partido por alguno de los bandos en pugna.
                    Durante esa década, se experimentaron distintas vías para profundizar la revolución, y se idearon nuevas formas de volverla eterna. La creencia era que todo estaba por hacerse y que todo lo que brotara, en su novedad, era bueno. Esta catarsis revolucionaria llegó a su máximo apogeo cuando el rey Luis XVI fue acusado de traición, encontrado culpable y ejecutado por medio de la guillotina (instrumento de castigo también inventado al fragor de la revolución) el 21 de enero de 1793.
                    Después de allí, Francia decretó el fin de la monarquía (que sin embargo volvería a entronizarse en varias ocasiones en este país). Este decreto, que inauguraba una primera república en Europa, se unía a la Declaración de los Derechos del Hombre y el Ciudadano (1789), el decreto de abolición del feudalismo (1789) y la redacción y entrada en funcionamiento de una Constitución (1791). Así, el ímpetu original revolucionario llevó a una radicalización del proceso, que sin embargo fue detenida en 1794, al tiempo en
                    que se abría una etapa de creciente lucha inter-partidaria que solo finalizaría con la llegada del general Napoleón Bonaparte al poder en 1799 (Soboul, 1977).
                    El ideal de Napoleón será la unidad interior, la reglamentación institucional y social a través de un código (el Código Napoleónico) y la extensión de los principios de la Revolución francesa
                    a otras partes del mundo. Esto último se intensificó después de 1804, cuando Napoleón se declaró emperador de Francia y se empeñó en una conquista de Europa. Como veremos más adelante,
                    esta campaña militar que terminó completamente en 1815 con la Batalla de Waterloo, también promovió de forma indirecta la agudización de la lucha de independencia en América Latina, por
                    efecto de la invasión napoleónica a España en 1808. La Revolución francesa había dado a luz a otro proceso que se intensificaría en el siglo XIX: la idea de formación de una nación.
                    """,
            Courses.HISTORIA_DE_LA_CULTURA.get(),
            0);

    private final String name;
    private final String prompt;
    private final Course course;
    private final int orderNumber;

    Topics(String name,
           String prompt,
           Course course,
           int orderNumber) {
        this.name = name;
        this.prompt = prompt;
        this.course = course;
        this.orderNumber = orderNumber;
    }

    public Topic get() {
        Topic topic = new Topic();
        topic.setName(name);
        topic.setPrompt(prompt);
        topic.setCourse(course);
        topic.setOrderNumber(orderNumber);
        return topic;
    }
}
