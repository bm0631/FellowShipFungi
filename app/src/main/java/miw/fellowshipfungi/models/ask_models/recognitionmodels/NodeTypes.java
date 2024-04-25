package miw.fellowshipfungi.models.ask_models.recognitionmodels;

public enum NodeTypes {
    Ask, Answer, Specie;

    public static NodeTypes typeNode(String node) {
        if (node == null) {
            return null;
        }
        return node.contains("Ask_") ? NodeTypes.Ask : NodeTypes.Specie;
    }
}
