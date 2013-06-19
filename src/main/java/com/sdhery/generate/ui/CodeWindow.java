package com.sdhery.generate.ui;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import com.sdhery.generate.bean.CodeVo;
import com.sdhery.generate.util.DBUtil;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: sdhery
 * Date: 13-6-19
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class CodeWindow extends JFrame {
    JPanel contentPanel;
    XYLayout xyLayout = new XYLayout();
    JLabel infoLabel = new JLabel();
    JLabel showLabel = new JLabel();

    JLabel jdbcUrlLabel = new JLabel();
    JTextField jdbcTextField = new JTextField();

    JLabel dataBaseUserNameLabel = new JLabel();
    JTextField dataBaseUserNameTextField = new JTextField();

    JLabel dataBasePWLabel = new JLabel();
    JPasswordField dataBasePWTextField = new JPasswordField();

    JLabel tableNameLabel = new JLabel();
    JTextField tableNameTextField = new JTextField();

    JLabel packageLabel = new JLabel();
    JTextField packageTextField = new JTextField();

    JLabel entityLabel = new JLabel();
    JTextField entityTextField = new JTextField();

    JLabel authorLabel = new JLabel();
    JTextField authorTextField = new JTextField();

    JLabel descriptionLabel = new JLabel();
    JTextField descriptionTextField = new JTextField();

    JLabel genPathLabel = new JLabel();
    JTextField genPathTextField = new JTextField();

    JButton genButton = new JButton();
    JButton existButton = new JButton();
    JButton genPathButton = new JButton();

    public static void main(String[] args) {
        try {
            new CodeWindow().pack();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public CodeWindow() throws Exception {
        init();
    }

    public void init(){
        contentPanel = (JPanel) getContentPane();
        //设置布局
        contentPanel.setLayout(xyLayout);

        infoLabel.setText("提示：");
        contentPanel.add(infoLabel,new XYConstraints(0, 10, -1, -1));
        contentPanel.add(showLabel,new XYConstraints(160, 10, -1, -1));

        jdbcUrlLabel.setText("数据库连接：");
        contentPanel.add(jdbcUrlLabel,new XYConstraints(0, 30, -1, -1));
        contentPanel.add(jdbcTextField,new XYConstraints(160, 30, 200, -1));

        dataBaseUserNameLabel.setText("数据库用户名：");
        contentPanel.add(dataBaseUserNameLabel,new XYConstraints(0, 50, -1, -1));
        contentPanel.add(dataBaseUserNameTextField,new XYConstraints(160, 50, 200, -1));

        dataBasePWLabel.setText("数据库密码：");
        contentPanel.add(dataBasePWLabel,new XYConstraints(0, 70, -1, -1));
        contentPanel.add(dataBasePWTextField,new XYConstraints(160, 70, 200, -1));

        packageLabel.setText("包名（小写）：");
        contentPanel.add(packageLabel,new XYConstraints(0, 90, -1, -1));
        contentPanel.add(packageTextField,new XYConstraints(160, 90, 200, -1));

        entityLabel.setText("实体类名（首字母大写）：");
        contentPanel.add(entityLabel,new XYConstraints(0, 110, -1, -1));
        contentPanel.add(entityTextField,new XYConstraints(160, 110, 200, -1));

        tableNameLabel.setText("表名：");
        contentPanel.add(tableNameLabel,new XYConstraints(0, 130, -1, -1));
        contentPanel.add(tableNameTextField,new XYConstraints(160, 130, 200, -1));

        authorLabel.setText("作者：");
        contentPanel.add(authorLabel,new XYConstraints(0, 150, -1, -1));
        contentPanel.add(authorTextField,new XYConstraints(160, 150, 200, -1));

        descriptionLabel.setText("描述：");
        contentPanel.add(descriptionLabel,new XYConstraints(0, 170, -1, -1));
        contentPanel.add(descriptionTextField,new XYConstraints(160, 170, 200, -1));

        genPathLabel.setText("生成文件的保存目录：");
        genPathButton.setText("选择");
        genPathTextField.setEditable(false);
        contentPanel.add(genPathLabel,new XYConstraints(0, 190, -1, -1));
        contentPanel.add(genPathTextField,new XYConstraints(160, 190, 200, -1));
        contentPanel.add(genPathButton,new XYConstraints(360, 190, 60, 18));

        genButton.setText("生成");
        existButton.setText("退出");

        existButton.addActionListener(new ExistButtonActionListener());
        genPathButton.addActionListener(new genPathButtonActionListener());
        genButton.addActionListener(new GenButtonActionListener());
        contentPanel.add(genButton,new XYConstraints(140, 220, -1, -1));
        contentPanel.add(existButton,new XYConstraints(210, 220, -1, -1));

        setTitle("代码生成器");
        setVisible(true);
        setDefaultCloseOperation(3);
        setSize(new Dimension(400, 300));

        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    class ExistButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }

    class genPathButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String currentFilePath = null;
            File path = new File(genPathTextField.getText()).getParentFile();
            JFileChooser jFileChooser = null;
            if (path!=null && path.exists()) {
                jFileChooser = new JFileChooser(path);
            } else {
                jFileChooser= new JFileChooser();
            }
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            File file = null;
            String currentFileName = null;
            int result = jFileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFilePath = jFileChooser.getSelectedFile().getPath();
                try {
                    file = new File(currentFilePath);
                    currentFileName = file.getAbsolutePath(); //完整路径
                    genPathTextField.setText(currentFileName);
                } catch (NullPointerException npe) {
                    String errorMsg = new String("status: Error by opening ");
                    System.err.println(errorMsg);
                }
            }
        }
    }

    class GenButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            showLabel.setText("");
            CodeVo codeVo = new CodeVo();
            if(StringUtils.isNotBlank(jdbcTextField.getText())){
                codeVo.setJdbcUrl(jdbcTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("数据库连接不能为空！");
                jdbcTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(dataBaseUserNameTextField.getText())){
                codeVo.setDataBaseUserName(dataBaseUserNameTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("数据库用户名不能为空！");
                dataBaseUserNameTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(new String(dataBasePWTextField.getPassword()))){
                codeVo.setDataBasePW(new String(dataBasePWTextField.getPassword()));
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("数据库密码不能为空！");
                dataBasePWTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(tableNameTextField.getText())){
                codeVo.setTableName(tableNameTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("表名不能为空！");
                tableNameTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(packageTextField.getText())){
                codeVo.setPackageValue(packageTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("包名不能为空！");
                packageTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(entityTextField.getText())){
                codeVo.setDomain(entityTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("实体类名不能为空！");
                entityTextField.requestFocus();
                return;
            }

            if(StringUtils.isNotBlank(authorTextField.getText())){
                codeVo.setAuthor(authorTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("作者不能为空！");
                authorTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(descriptionTextField.getText())){
                codeVo.setDescription(descriptionTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("描述不能为空！");
                descriptionTextField.requestFocus();
                return;
            }
            if(StringUtils.isNotBlank(genPathTextField.getText())){
                codeVo.setGetPath(genPathTextField.getText());
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("生成文件的保存目录不能为空！");
                genPathTextField.requestFocus();
                return;
            }

            boolean tableExist = new DBUtil().checkTableExist(codeVo);
            if(tableExist){
                try{
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                showLabel.setForeground(Color.red);
                showLabel.setText("数据库中的表不存在！");
            }

        }
    }
}
