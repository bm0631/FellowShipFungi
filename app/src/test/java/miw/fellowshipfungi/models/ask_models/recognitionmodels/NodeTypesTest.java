package miw.fellowshipfungi.models.ask_models.recognitionmodels;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodeTypesTest {

    @Test
    public void testTypeNodeWithAskNode() {
        String askNode = "Ask_001";
        NodeTypes result = NodeTypes.typeNode(askNode);
        assertEquals(NodeTypes.Ask, result);
    }

    @Test
    public void testTypeNodeWithSpecieNode() {
        String specieNode = "Species_001";
        NodeTypes result = NodeTypes.typeNode(specieNode);
        assertEquals(NodeTypes.Specie, result);
    }

    @Test
    public void testTypeNodeWithEmptyNode() {
        String emptyNode = "";
        NodeTypes result = NodeTypes.typeNode(emptyNode);
        assertEquals(NodeTypes.Specie, result);
    }

    @Test
    public void testTypeNodeWithNullNode() {
        String nullNode = null;
        NodeTypes result = NodeTypes.typeNode(nullNode);
        assertEquals(nullNode, result);
    }

    @Test
    public void testTypeNodeWithInvalidNode() {
        String invalidNode = "InvalidNode";
        NodeTypes result = NodeTypes.typeNode(invalidNode);
        assertEquals(NodeTypes.Specie, result);
    }
}
