package uniandes.dpoo.taller1.interfaz;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import uniandes.dpoo.taller1.modelo.Categoria;

/**
 * Panel donde se muestran las categor�as de la librer�a
 */
@SuppressWarnings("serial")
public class PanelCategorias extends JPanel implements ItemListener
{
	// ************************************************************************
	// Atributos
	// ************************************************************************

	/**
	 * Ventana que contiene al panel
	 */
	private InterfazLibreria ventana;

	// ************************************************************************
	// Componentes gr�ficos
	// ************************************************************************

	/**
	 * ComboBox con la informaci�n de las categor�as
	 */
	private JComboBox<Categoria> cbbCategorias;

	// ************************************************************************
	// Constructores
	// ************************************************************************

	/**
	 * Construye el nuevo panel y lo conecta con la ventana que lo contiene
	 * 
	 * @param interfazLibreria La ventana dentro de la que se encuentra el panel
	 */
	public PanelCategorias(InterfazLibreria interfazLibreria)
	{
		ventana = interfazLibreria;
		setBorder(new TitledBorder("Categor�as"));
		setLayout(new BorderLayout());
		cbbCategorias = new JComboBox<>();
		cbbCategorias.addItemListener(this);
		add(cbbCategorias);
	}

	// ************************************************************************
	// M�todos
	// ************************************************************************

	/**
	 * Actualiza las categor�as que se muestran en el combo box
	 * 
	 * @param categorias
	 */
	public void actualizarCategorias(Categoria[] categorias)
	{
		cbbCategorias.removeAllItems();
		for (int i = 0; i < categorias.length; i++)
		{
			Categoria categoria = categorias[i];
			cbbCategorias.addItem(categoria);
		}
	}

	// ************************************************************************
	// M�todos implementados de la interfaz ItemListener
	// ************************************************************************

	/**
	 * Este m�todo es el que se invoca cuando se selecciona una categor�a nueva en
	 * el combobox.
	 *
	 * Al hacer una selecci�n, se invoca al m�todo cambiarCategoria de
	 * InterfazLibreria
	 */
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			int seleccionado = cbbCategorias.getSelectedIndex();
			if (seleccionado != -1)
			{
				Categoria categoria = cbbCategorias.getItemAt(seleccionado);
				ventana.cambiarCategoria(categoria);
			}
		}
	}

	public JComboBox<Categoria> getCbbCategorias() {
		return cbbCategorias;
	}
	//hola
}
