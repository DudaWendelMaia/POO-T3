import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class CadastroCliente extends JFrame {
    //campos de texto
    private JTextField codigoTextField;
    private JTextField nomeTextField;
    private JTextField emailTextField;
    
    //areas de texto
    private JTextArea mensagemTextArea;
    private JTextArea clientesTextArea;
    // Mapa armazenar clientes (codigo como chave)
    private Map<String, Cliente> clientes;

    public CadastroCliente() {
        clientes = new TreeMap<>(); //TreeMap para manter a ordem crescente dos codigos

        // Criacao dos componentes da interface grafica
        JLabel codigoLabel = new JLabel("Código:");
        codigoTextField = new JTextField(20);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField(20);

        JLabel emailLabel = new JLabel("E-mail:");
        emailTextField = new JTextField(20);

        JButton cadastrarButton = new JButton("Cadastrar");
        // Configuracao do evento de clique do botao "Cadastrar"

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        JButton limparButton = new JButton("Limpar");
        // Configuracao do evento de clique do botao "Limpar"

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        JButton mostrarButton = new JButton("Mostrar Clientes");
        // Configuracao do evento de clique do botao "Mostrar Clientes"
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });

        JButton finalizarButton = new JButton("Finalizar");
        // Configuracao do evento de clique do botao "Finalizar"
        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });

        mensagemTextArea = new JTextArea(10, 30); // area de texto para exibir mensagens
        mensagemTextArea.setEditable(false); //area de texto somente leitura
        JScrollPane scrollPane1 = new JScrollPane(mensagemTextArea); //add barra de rolagem a area de texto

        clientesTextArea = new JTextArea(10, 30); // area de texto para exibir lista de clientes
        clientesTextArea.setEditable(false); 
        JScrollPane scrollPane2 = new JScrollPane(clientesTextArea);

        // Configuracao do layout da janela
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //layout do painel
        GridBagConstraints constraints = new GridBagConstraints(); //restricoes para o posicionamento dos componentes no painel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST; // componentes para a esquerda
        constraints.insets = new Insets(5, 5, 5, 5); // margens internas dos componentes

        // Adicao dos componentes a janela
        panel.add(codigoLabel, constraints);
        constraints.gridx = 1;
        panel.add(codigoTextField, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;

        panel.add(nomeLabel, constraints);
        constraints.gridx = 1;
        panel.add(nomeTextField, constraints);
        constraints.gridy = 2;
        constraints.gridx = 0;

        panel.add(emailLabel, constraints);
        constraints.gridx = 1;
        panel.add(emailTextField, constraints);
        constraints.gridy = 3;
        constraints.gridx = 0;

        panel.add(cadastrarButton, constraints);
        constraints.gridx = 1;
        panel.add(limparButton, constraints);
        constraints.gridy = 4;
        constraints.gridx = 0;

        panel.add(mostrarButton, constraints);
        constraints.gridx = 1;

        panel.add(finalizarButton, constraints);
        constraints.gridwidth = 2;
        constraints.gridy = 5;
        constraints.gridx = 0;

        constraints.fill = GridBagConstraints.BOTH; // Preenche na horizontal e na vertical
        panel.add(scrollPane1, constraints); //barra de rolagem
        constraints.gridy = 6;
        panel.add(scrollPane2, constraints); //barra de rolagem

        setTitle("Cadastro de Clientes"); //titulo janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fechar janela
        setResizable(false); // redimensionamento da janela
        setContentPane(panel); // painel como conteudo da janela
        pack(); // Ajusta o tamanho da janela com base nos componentes
        setLocationRelativeTo(null); // Centraliza janela na tela
    }

    private void cadastrarCliente() {
        String codigo = codigoTextField.getText().trim();
        String nome = nomeTextField.getText().trim();
        String email = emailTextField.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty() || email.isEmpty()) {
            exibirMensagem("Preencha todos os campos.");
            return;
        }

        try {
            if (clientes.containsKey(codigo) || clientes.values().stream().anyMatch(cliente -> cliente.getEmail().equals(email))) {
                throw new IllegalArgumentException("Código ou e-mail já existente.");
            }

            Cliente cliente = new Cliente(codigo, nome, email);
            clientes.put(cliente.getCodigo(), cliente);
            
            limparCampos();
        } catch (IllegalArgumentException e) {
            exibirMensagem(e.getMessage());
        }
        exibirMensagem("--------------------------------------");
        exibirMensagem("   Cliente cadastrado com sucesso.");
        exibirMensagem("--------------------------------------");

    }

    private void limparCampos() {
        codigoTextField.setText("");
        nomeTextField.setText("");
        emailTextField.setText("");
        mensagemTextArea.setText("");
    }

    private void exibirMensagem(String mensagem) {
        mensagemTextArea.append(mensagem + "\n");
    }

    private void mostrarClientes() {
        clientesTextArea.setText("");

        if (clientes.isEmpty()) {
            exibirMensagem("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente cliente : clientes.values()) {
            String codigo = cliente.getCodigo();
            String nome = cliente.getNome();
            String email = cliente.getEmail();
            clientesTextArea.append("Código: " + codigo + " | Nome: " + nome + " | E-mail: " + email + "\n");
        }
    }

    private void finalizar() {
        System.exit(0);
    }

    private class Cliente {
        private String codigo;
        private String nome;
        private String email;

        public Cliente(String codigo, String nome, String email) {
            this.codigo = codigo;
            this.nome = nome;
            this.email = email;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new CadastroCliente().setVisible(true);
            }
        });
    }
}
