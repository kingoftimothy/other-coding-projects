from locale import currency
from tkinter import *
from tkinter import ttk
from tkinter import messagebox
import requests
import json
from currency_symbols import CurrencySymbols

# USING EXCHANGERATE-API for currency exchange rates
# https://www.exchangerate-api.com/docs/python-currency-api
# Supports all ISO 4217 Currency Codes

root = Tk()
root.title("Timothy's Currency Converter")
root.geometry("500x500")

# API KEY URL FOR CODES
codes_url = "https://v6.exchangerate-api.com/v6/107ceb7f24620b270bfa1e73/codes"
# Making Request to API and gives data as dictionary
api_codes_request = requests.get(codes_url)
codes_data = api_codes_request.json()
# Gets the conversion rates dictionary of the data from the API
acceptable_codes = codes_data.get("supported_codes")

# Create Tabs
app_screen = ttk.Notebook(root)
app_screen.pack(pady=5)

# Create Currency Frame
frame_currency = Frame(app_screen, width=480, height=480)
frame_currency.pack(fill="both", expand=1)
# Create a Frame for Currency Codes
myFrame = Frame(app_screen, width=480, height=480)
myFrame.pack(fill=BOTH, expand=1)
# Create Conversion Frame
frame_conversion = Frame(app_screen, width=480, height=480)
frame_conversion.pack(fill="both", expand=1)

# Add our Tabs from the three Frames
app_screen.add(frame_currency, text="Currency")
app_screen.add(myFrame, text="Currency Codes")
app_screen.add(frame_conversion, text="Convert")

# Disable 3rd tab
app_screen.tab(2, state='disabled')

# Create A Canvas
my_canvas = Canvas(myFrame)
my_canvas.pack(side=LEFT, fill=BOTH, expand=1)
# Add a Scrollbar to the Canvas
myscrollbar=ttk.Scrollbar(myFrame,orient=VERTICAL, command=my_canvas.yview)
myscrollbar.pack(side=RIGHT,fill=Y)
# Configure the Canvas
my_canvas.configure(yscrollcommand=myscrollbar.set)
my_canvas.bind('<Configure>', lambda e: my_canvas.configure(scrollregion=my_canvas.bbox("all")))
# Creating Another Frame Insdie Canvas
second_frame = Frame(my_canvas)
# Add that New Frame to Window in Canvas
my_canvas.create_window((0,0), window=second_frame, anchor="nw")
# Dataset of Currency Codes Table from the Acceptable Codes
total_rows = len(acceptable_codes)
total_columns = len(acceptable_codes[0])
# Displays the Table
for i in range(total_rows):
    for j in range(total_columns):         
        graph = Entry(second_frame, width=20, fg='white', font=('Arial', 16,'bold'))
        graph.grid(row=i, column=j)
        graph.insert(END, acceptable_codes[i][j])

########################
# GUI FOR CONVERTER
def lock():
    # Warning Error
    if not entry_home.get() or not entry_conversion.get():
        messagebox.showwarning("WARNING!", "ALL THE FIELDS AREN'T FILLED OUT!")
    # Checks if the Conversion Code entered is Valid
    elif any(entry_home.get() in sublist for sublist in acceptable_codes) and any(entry_conversion.get() in sublist for sublist in acceptable_codes):
        # API KEY URL
        url = "https://v6.exchangerate-api.com/v6/107ceb7f24620b270bfa1e73/latest/%s" % entry_home.get()
        # Making Request to API and gives data as dictionary
        api_request = requests.get(url)
        data = api_request.json()
        # Gets the conversion rates dictionary of the data from the API
        conversion_rates = data.get("conversion_rates")
        
        # Change Label to show rate value for currencies
        label_for_rate_value.config(text=f'{conversion_rates.get(entry_conversion.get())}')
        # Disable entry boxes
        entry_home.config(state="disabled")
        entry_conversion.config(state="disabled")
        # Enable tab
        app_screen.tab(2, state='normal')
        # Change tab fields
        label_amount.config(text=f'Amount of {entry_home.get()} To Convert To {entry_conversion.get()}')
        label_converted.config(text=f'Equals This Many {entry_conversion.get()}')
        button_convert.config(text=f'Convert From {entry_home.get()}')
        # In the Convert Tab, change to Respesctive Symbol of Currency
        label_amount_symbol.config(text=f'{CurrencySymbols.get_symbol(entry_home.get())}')
    else:
        # Gives Warning
        messagebox.showwarning("WARNING!", "NOT A VALID CURRENCY CODE! CAPITALIZE AND CHECK WITH TABLE")
        

def unlock():
    # Enable entry boxes
    entry_home.config(state="normal")
    entry_conversion.config(state="normal")
    # Clear label
    label_for_rate_value.config(text=f'')
    # Disable tab
    app_screen.tab(2, state='disabled')
    clear()

frame_home = LabelFrame(frame_currency, text="Your Home Currency (Please Type Currency Code)")
frame_home.pack(pady=20)

# Home currency entry box
entry_home = Entry(frame_home, font=("Helvetica", 24))
entry_home.pack(pady=10, padx=10)

# Conversion Currency Frame
conversion = LabelFrame(frame_currency, text="Conversion Currency (Please Type Currency Code)")
conversion.pack(pady=10)

# convert to label
label_conversion = Label(conversion, text="Currency to Convert To...")
label_conversion.pack(pady=10)

# Convert To Entry
entry_conversion = Entry(conversion, font=("Helvetica", 24))
entry_conversion.pack(pady=10, padx=10)

# rate label
label_rate = Label(conversion, text="Current Conversion Rate")
label_rate.pack(pady=10)

# Conversion Rate Entry
label_for_rate_value = Label(conversion, font=("Helvetica", 24))
label_for_rate_value.pack(pady=10, padx=10)

# Button Frame
frame_button = Frame(frame_currency)
frame_button.pack(pady=20)

# Create Lock/Unlock Buttons
button_lock = Button(frame_button, text="Lock", command=lock)
button_lock.grid(row=0, column=0, padx=10)

button_unlock = Button(frame_button, text="Unlock", command=unlock)
button_unlock.grid(row=0, column=1, padx=10)

######################
# GUI FOR CONVERSION
def convert():
    # Warning if User doesn't fill out Entry Amount
    if not entry_amount.get():
        messagebox.showwarning("WARNING!", "ALL THE FIELDS AREN'T FILLED OUT!")
    else:
        # Clear Converted Entry Box
        entry_converted.delete(0, END)

        # Convert
        conversion = float(label_for_rate_value.cget("text")) * float(entry_amount.get())
        # Convert to two decimals
        conversion = round(conversion,2)
        # Add comma formatting to Conversion
        conversion = '{:,}'.format(conversion)
        # Update Entry Box and Repsective Currency Symbol
        entry_converted.insert(0, f'{CurrencySymbols.get_symbol(entry_conversion.get())}{conversion}')

# Clears entries
def clear():
    entry_amount.delete(0,END)
    entry_converted.delete(0,END)

label_amount = LabelFrame(frame_conversion, text="Amount to Convert")
label_amount.pack(pady=20)

# Currency Symbol
label_amount_symbol = Label(label_amount, text="", width=10)
label_amount_symbol.pack(side="left")
# Entry Box For Amount
entry_amount = Entry(label_amount, font=("Helvetica", 24))
entry_amount.pack(side="left", pady=10, padx=10)

# Convert Button
button_convert = Button(frame_conversion, text="Convert", command=convert)
button_convert.pack(pady=20)

# Equals Frame
label_converted = LabelFrame(frame_conversion, text="Converted Currency")
label_converted.pack(pady=20)

# Converted entry
entry_converted = Entry(label_converted, font=("Helvetica", 24), bd=0, bg="Black")
entry_converted.pack(pady=10, padx=10)

# Clear Button
button_clear = Button(frame_conversion, text="Clear", command=clear)
button_clear.pack(pady=20)

# Fake Label for spacing
label_spacer = Label(frame_conversion, text="", width=70)
label_spacer.pack()

root.mainloop()