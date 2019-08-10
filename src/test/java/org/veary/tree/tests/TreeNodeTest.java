/*
 * MIT License
 *
 * Copyright (c) 2019 ColonelBlimp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.veary.tree.tests;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.veary.tree.TreeNode;

public class TreeNodeTest {

    private static final String ROOT_DATA = "Root";
    private static final String CHILD1 = "Child-1";
    private static final String CHILD2 = "Child-2";
    private static final String CHILD11 = "Child-11";
    private static final String CHILD12 = "Child-12";
    private static final String CHILD21 = "Child-21";
    private static final String CHILD211 = "Child-211";
    private static final String CHILD212 = "Child-212";
    private static final String CHILD2121 = "Child-2121";
    private static final String CHILD2122 = "Child-2122";
    private static final String CHILD2123 = "Child-2123";
    private static final String CHILD2124 = "Child-2124";

    @Test
    public void instantiation() {
        TreeNode<String> root = new TreeNode<>(ROOT_DATA);
        Assert.assertNotNull(root);
        Assert.assertEquals(root.getData(), ROOT_DATA);
        Assert.assertTrue(root.isRoot());

        List<TreeNode<String>> children = root.getChildren();
        Assert.assertNotNull(children);
        Assert.assertTrue(children.isEmpty());

        Assert.assertTrue(root.isRoot());
        Assert.assertTrue(root.isLeaf());
    }

    @Test(dependsOnMethods = "instantiation")
    public void addChildLeft() {
        TreeNode<String> root = new TreeNode<>(ROOT_DATA);
        TreeNode<String> child = root.addChild(CHILD1);

        Assert.assertNotNull(child);
        Assert.assertTrue(child.isLeaf());
        Assert.assertFalse(child.isRoot());
        Assert.assertEquals(child.getParent().getData(), ROOT_DATA);

        Assert.assertFalse(root.getChildren().isEmpty());
        Assert.assertTrue(root.getChildren().size() == 1);
    }

    @Test
    public void iteration() {
        TreeNode<String> root = buildTree();

        for (TreeNode<String> node : root) {
            System.out.println(node.getData() + " - Level(" + node.getLevel() + "), root: "
                + node.getRoot().getData());
        }
        System.out.println();
    }

    @Test(
        expectedExceptions = NoSuchElementException.class,
        expectedExceptionsMessageRegExp = "This is the root node.")
    public void getParentException() {
        TreeNode<String> root = new TreeNode<>(ROOT_DATA);
        root.getParent();
    }

    @Test
    public void findNode() {
        TreeNode<String> root = buildTree();
        Assert.assertTrue(root.size() == 12);

        Optional<TreeNode<String>> resultFound = root.findNode(
            element -> {
                if (element.equals(CHILD2124)) {
                    return true;
                }
                return false;
            });

        Assert.assertTrue(resultFound.isPresent());
        Assert.assertEquals(resultFound.get().getData(), CHILD2124);

        Optional<TreeNode<String>> resultNotFound = root.findNode(
            element -> {
                if (element.equals("TEST DATA")) {
                    return true;
                }
                return false;
            });

        Assert.assertTrue(resultNotFound.isEmpty());
    }

    @Test
    public void ordering() {
        TreeNode<String> root = new TreeNode<>("BALANCE");
        TreeNode<String> netWorth = root.addChild("NET WORTH");
        netWorth.addChild("ASSETS");
        netWorth.addChild("LIABILITIES");

        TreeNode<String> incomeExpenses = root.addChild("INCOME & EXPENSES");
        incomeExpenses.addChild("INCOME");
        incomeExpenses.addChild("EXPENSES");
        root.addChild("OPENING BALANCE");

        for (TreeNode<String> node : root) {
            System.out.println(node.getData() + " - Level(" + node.getLevel() + "), root: "
                + node.getRoot().getData());
        }
        System.out.println();
    }

    private TreeNode<String> buildTree() {
        TreeNode<String> root = new TreeNode<>(ROOT_DATA);
        TreeNode<String> child1 = root.addChild(CHILD1);
        TreeNode<String> child2 = root.addChild(CHILD2);
        child1.addChild(CHILD11);
        child1.addChild(CHILD12);

        TreeNode<String> child21 = child2.addChild(CHILD21);
        child21.addChild(CHILD211);
        TreeNode<String> child212 = child21.addChild(CHILD212);
        child212.addChild(CHILD2121);
        child212.addChild(CHILD2122);
        child212.addChild(CHILD2123);
        child212.addChild(CHILD2124);

        return root;
    }
}
