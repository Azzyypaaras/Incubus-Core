package net.id.incubus_core;

public class IncubusCoreInit {
    // I'm not even going to check git blame for this because we all know who did this.
    public static final String HOLY_CONST = "hhddmmmmNNNNNNmmmdddhhhhhhhhhhhhhhhhhhhhhhhhhhhhhdMMmyo-::-:::::::////++++++++/////::----------------------------------------::::::::://///////++syddd\n" +
            "MMMMMNNNmmmmmmNNNNMMMMNNddhhhhhhhhhhhhhhhhhhhhhhhdMMNhy+oyhdmmNNNNNNmmmmmmmmmmmmmNNNNNNNmmdhyyso+/:-------------------------::::::::::////////////+osh\n" +
            "mdhhhyyyyyyyyyyyyyyhhdmNNddhhhyhyhhhhhhyhhhyhhhmNMMMMNmmddhhhyyyyyyyyyyyyyyyyyyyyyyyyyyhhhhhddmmmmNmmdhso+:-----------------:::::::::///////////////+o\n" +
            "yyyyyyyyyyyyyyyyyyyyyymmNdhyyyyyyyyyyyyyyyhdmNMMMNmdhyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhhhhhhddmmmmmhyo+:---------:::::::::://////////////+sh\n" +
            "yyyhhhhhhhhhhhhhhhhhhdmNmdhyyyyyyyyyyyyhdNMMMNNdhyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhhhhhhhhhhhhhdddmmmNmdys+/:-::::::::://///////////+oydNN\n" +
            "dddddmmmNNNNNNNNNNNNNNMNdhhhhhhhhhhhhdmMMMNmdhhhhyhhhhhhyyhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhddddddddddddddmmmmNNNmdyo+/:::::///////////+shmNNdh\n" +
            "mmNNMMMMMNNmmmmmmmNNNMMMMNmddhhhhhhdNMMNmdhhhyyyhhhhhyyyhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhddddddddddddddmmmmmmmmmmNNNmdyo+//////////+shmNmdhhh\n" +
            "MMNNmdhhyyyyyyyyyyyyyyhhdmNMNNdhhdNMMNmdyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhhhhhhhhhhhhhddddddddddddmmmmmmmmmNNNNdyo+////+shNNmdhyyyy\n" +
            "mdhyyyyyyyyyyyyyyyyyyyyyyyyhmNNNMMMNmhyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhhhhhhhhhhhdddddddddddddmmmmmmmmmmmmNNNmhsoydNNmdhyyyyyy\n" +
            "hyyyyyyyyyyyyyyyyyyyyyyyyyyyhmNMMNmdhyyyyyyyyyhhhhhhhhhddddddddddddddhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhdddddddddddddmmmmmmmmmmmmmmmmmmmmmNNNMNNmdhyyyyyyyy\n" +
            "hhhhhhhhhhhhhhhhhhhhhhhhhhhhmNMNmdhhhhddddmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmNNNNmmmmmmmmddddddddddddddddddddddmmmmmmmmmmmmmmmmmmmmmmmmmNMMMNdhhhhhhhhhhh\n" +
            "yyyyyyyyyyyyyyyyyyyyyyyyyydmNMNmdddmmmmmmmmmmmmmmmmmmmmmmmmmmmdddddddddddddddddddddddddmmmmmmmmmmmmddddddddddddmmmmmmmmmmmmmmmmmmmmmNMMNmdhyyyyyyyyyyy\n" +
            "yyyyyyyyyyyyyyyyyyyyyyyyydmNNNmmmmNmmNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNMMMMMMNNNNNNmmmddhhhhhhhhddmmmmmmmmmmdddddmmmmmmmmmmmmmmmmmNNMMmdyyyyyyyyyyyyy\n" +
            "yyyyyyyyyyyyyyyyyyyyyyyhmNMMNNNMMMMNNNNNNmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmNNNNNNNNNNNNNMMMMMMMMMNNNmmddhhhhhddmmmmmmmmmmmmmmmmmmmmmmmmNNMNdhyyyyyyyyyyyyy\n" +
            "yyyyyyyyyyyyyyyyyyhhdmNNMMMMMNNmdddhdddddddddddddddddddddmmmmmmmmmmmmmNNNNNNNNNNNNNNNNNNNNNNNNMMMMMMMMMNNmmmdddddmmmmmmmmmmmmmmmmmNMMMmdhyhyyyyyyyyyyy\n" +
            "yyyyyyyyyyyyyyhhdmmNMMMNNmddhhyyyyyyyyyyyyyyyyhhhhhhhhhhhhhhhhhhhhhhdddddddddmmmmmmmmNNNNNNNMMMMMMMMMMMMMMMMMMMNNmmmmmmmmmmmmmmmmmNMMNddyyhyyyyyyyyyyy\n" +
            "yyyyyyyyyyyyyhdNMMMNmdhyyyssssssssssssssssssyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhhhdmNMMMMNNNNNNNNNNNMMMMMMMMMMMMMNNmmmmmmmmmmmNMMmdyyyyyyyyyyyyyyy\n" +
            "yyyyyyyyyyydmNMMNmhysssssssssssssssssssssssssyyhhho+ssssssssssyyyyssssssyyyyssyyyyyyhmNMMNmdhyyyhhhddmmNNNNNMMMMMNNNMMMMMMNNmmmmmNNMMddyyyyyyyyyyyyyyy\n" +
            "yyyyyyyyyydNNNmdysssssssssoosssssssssssssssyyhdmdd+/+osssysssyhhhhhyyyyyyyyyyyyyyydmNMNmdhyyyyyyyyyyyhhhdmmmNNNNMMMNNNNMMMMMMMMNNMMMNddyyyyyyyyyyyyyyy\n" +
            "hhhhhhhhhhNNMmmyssssssso+/::+ossssssssssssyhdddmddo/:oosyyyyyyhhdddddhyyyyyyyyyyydmMNmhyyyyyyyyyyyyyyyyyyyhhddmmmNNNNNNMMMMMMMMMMMMMNdhhhhhhhhhhhhhhhh\n" +
            "yyyyyyyyyhmNMmdssssso+/::::/oosssssssssssyyddssyhddo++ssyssssyyhddddmmddhyysssssyhhdhyssssssssssssssssssssssssyyyhddmmmNNNMMMMMNNNMMmdhyyyyyyyyyyyyyyy\n" +
            "yyyyyyyyyhmNMmdsoo+/::::::/oooooooooooosyyhdso/shmNNNNmmmddyysyyhhhddddmmmdhyysssssssssssssssssssssssssssssssssssssyyhdmmNNMMMMNNNNMmdyyyyyyyyyyyyyyyy\n" +
            "yyyyyyyyyhmNMmdsoo/::::::/+oooooooooossyhhdhshymNMMMNNmmNNNmdhyyhhhddmddhhmNNNmdhyyssssssssssssssssssssssssssssssyyhdmNNMMMMNNmmNNNMddyyyyyyyyyyyyyyyy\n" +
            "hhhhhhhhhhmNMmdyss+/::::/+ossssssssssyyhdmmhNmNdyshdmmhyyyhddhhyyhdddmNmdhyyyhmmNNNNNmmddhhhyyyyyyyyyyyyyyhhddmNNNNNmdhhmmNNNNNNNNNNddhhhhhhhhhhhhhhhh\n" +
            "hhhhhhhhhhdNNNdhsso/:-:/+oossoooosssyyhddmNMMmy+:-:oydmmdyssssssyyhdddmmNNdysooossyhddmmNNNNNNNNNNNNNNNMMMMNNNmddhysssoshdNNmmmNNNNNddhhhhhhhhhhhhhhhh\n" +
            "yyyyyyyyyhhmNMmdsoo/:::++oooooooossyyyhhdmMMds+:----:+sdmNmdhysoossyhhhddmNNdyo+++++++++ooossyyhhdmmNNNNMMNNMMMNNNNmmdhhdmNNmmmmmmNNddyyyyyyyyyyyyyyyy\n" +
            "yyyyyyyyyhhhmNNmhoo/::/+oooooooossyyyhhhdNNhs/:::::::--:+shmNNNmdhyyyyyyyhhdNNmho++++///++syhmNNMNNNNNMMMMMMMMMMMMMMNNmNNNNNddmmmmmNddyyyyyyyyyyyyyyyy\n" +
            "hhhhhhhhhhhhmNNNmhso/:++ooooooosyyhhhdhmmNhs/::::::::::----:/oyhdmNNNNNmmmmNmmdyo++++oshmNNMNmdys/:.-oymNNNNNNNMMMMNhyoydmNmdmmmmNNNmdhhhhhhhhhhhhhhhh\n" +
            "hhhhhhhhhhdddmNMNmdsooooooooooyyhhddddymmmyo////////:::::------::://++ooossoo++///+shmNNmdy+:.``  ``.+ydddddmmmmmNMdhoohdNNmdmmmmNNNmdhhhhhhhhhhhhhhhh\n" +
            "hhhhhhhhhhhhhhdNNMNdyoooooooosyhhhdddyhdNdy+////////:::::::::::::::///////////////syhho/-.```   `-/shhddddddddddmmNmhsshdNNhhddmmmNNmmhhhhhhhhhhhhhhhh\n" +
            "yyyyhhhhhhhhhhhdmNMMmdyooooosyhhhhhhhodhNhy/////////:::::::::::::://///////////////:-.````        .-+syhhhhhhddmNMNmhsshmNmhhhhdddmmmmdhhhhhhhhhhhhhhh\n" +
            "hhhhhhhhhhhhhhhdddmNMMNmhysyyhdhddddysddNhs++//////////+oossyyys+//:////:::::::::////:.```           `-/oyhmmNMMNhso//yhmNmhhhhdddmNNmdhhhhhhhhhhhhhhh\n" +
            "hhhhhhdddddddddddddmNNMMMNmmddddddddsddNmhs++++++oyhdNNMMMMMMMmhs/::::::::----..........--://///::-----:+shmmmho:..:/+ydNNdhhhddmmmNNmmddddddddddddddd\n" +
            "dhhhhhdddddddddddddddmmNMMMMNNmmddddymdMdho+osydNNMMNmmNNNNNMMmyo::-----..............`````...--::/+++++++//-..````.:+hdNNdhhhddmmmNNmmddddddddddddddd\n" +
            "hhhhhhhhhhhhhhddddddddddmmNNMMMMNNmdhmmNhhshmNMNmyyo:.`+smmmmmhs:..:oydhs+:.....````````````````````````````````````:ohdNmhyyhhhdddmNNmdhhhhhhhhhhhhhh\n" +
            "dhhhhhhhhhhhhdddddddddddmmmmmNNNMMMMMMMMNMNMmho:.`---//shddddho/`.:yhdy+/++:...`````````````````````````````````````:shmNmyyyhhddddmmNmmdddddddddddddd\n" +
            "ddddhhddddddddddddddmmmmmmmmmmmmmNMMNMNMMmy+:.`````:/yhhhhddy+:```+yys/----:..``````````````````````````````````````:yhNNdhhhdddmmmmNNmmdddddddddddddd\n" +
            "mmddddddddddddddddmmmmmmmmmmmmmmmNMMMNNmo:-.``     `-/shdddo/.````.+yys+:/o/:.``````````````````````````````````````/ydNNdhhhddmmmmmNNNmmdmdmddddddddd\n" +
            "ddddddhhdddddddddddddmmmmmmmmmmmmMMNMNd+:`.``       .:+ss+-.````````.-/++/-.````````````````````..``````````````````+ydNmhhhhhdddddmmNNmmddddddddddddd\n" +
            "mdddddhhhhhdddddddddddmmmmmmmmmmNMMNMmy+.`.-::::://++/-.```````````````````````````````````````/y+/.````````````````symNmyyyhhhdddddmmNmmddddddddddddd\n" +
            "NNmmdddddddddddddddddmmmmmmmmmmmNMMNNms+.``````....```````````````````````````````````````````/s//.````````````````.yhNNdyyhhhddddddmmNNmmmmmmmmmmmmdd\n" +
            "mMNNmmmddddddmmmmmmmmmmmmmmmmmmNMMNNNNy+.``````````````````````````````````````````````````-+yy+:``````````````````+ymNmhhhhdddmmmmmmNNNmmmmmmmmmmmmmm\n" +
            "dmMMNmmmmdddddmmmmmmmmmmmmmmmmmNMMNNmNy+-``````````````````````````````````-://::----::/oshhy+-.``````````````````+ymNmhyhhdddddmmmmmNNNNmmmmmmmmmmmmm\n" +
            "ddmMMNNmmmmdddddddmmmmmmmmmmmmmNMMNNmmh+-```````````````````````````````./yys+///+++++//:-..````````````````````.ohmmdyyyhhhhhhddddddmmNNmmmmmmmmmmmmm\n" +
            "dddmMMMNmmmmmddddmmmmmmmmmmmmmNNMNNddmd+:````````````````..`````````.-/sys/-```````````````````````````````````-shmmdyyyhhhhhhhddddddmmNNmmmmmmmmmmmmm\n" +
            "dddddNMMNNmmmmmmmmmmmmmmmmmmmmNMMNNdmmN+/`````````````````.....--://+/:-.`````````````````````````````````````/ydNmdyyhhddddddddmmmmmmNNNNmmmmmmmmmmNN\n" +
            "ddddddNMMMNNmmmmmmmmmmmmmmmmmNMMMNmdmmNo+```````````````````````````````````````````````````````````````````.ohmNmhyyhhdddddddmmmmmmmmNNNNmmmmmmmmmNNN\n" +
            "hhhhhhhmMMMNNmmmmmmmmmmmmmmmmNMMNNdhdmNy+.`````````````````````````````````````````````````````````````````-shNmdyyyyhhhhhddddddddddddmNNNmmmmmmmmNNNN\n" +
            "hhhhhhhhdNMMNNmmmNNNmmmmmmmmmNNMNmhhhdmm+:````````````````````````````````````````````````````````````````:ydNmhssyyyhhhhhhhhhddddddhddNNNmmmmmmmmNNNN\n" +
            "hhhhhhhdhdNMMNNmmmNNNNNmmmmmmMMNNmhhddmNs/.``````````````````````````````````````````````````````````````+ymmmyssyyhhhhhhhhdddddddddyddNNNmmmmmmmNNNNN\n" +
            "dddddddddddmMMMNmmmNNNNNNNNNNMMNNddddmmNmo/````````````````````````````````````````````````````````````.ohmmdyssyyhhddddddddddmmmmmdsmdNNNNmmmmNNNNNNN\n" +
            "hhhhhhhhhhhhdNMMNmmdmNNNNNNNMMMNmhhddmmmNmo/``````````````````````````````````````````````````````````.ohmmhyssyyhhhhhhddddddddddmmhymmMNNNmmNNNNNNNNN\n" +
            "hhhhhhhhhhhhhhmMMMmmddmNNNNNMMNNdhyhhddmmNmy/.```````````````````````````````````````````````````````.ohmmhssssyyyhhhhhhhhhhddddddhsddNNNmmmmmNNNNNNNN\n" +
            "hhhhhhhhhhhhhhhdNMMNmdhdmNNMMMNmhyyhhddddmmNms:.```````````````````````````````````````````````````..oydmyssssyyyyyyyhhhhhhhdddddhshdNMNNmmmmmNNNNNNNN\n" +
            "dhhhhhhhhhhhhhhhdmMMMNdhhdNMNNmhhhhddmmmmmmNNNds/.`````````````````````````````````````````````...--+yhmhssssyyhhyhhhhhddddddddddshdNMMNNNmNNNNNNNNNNN\n" +
            "dddddddddddddddddddNMMNmmmNNmdhhhhddmmmmmmmmmNNNmy+:.`````````````````````````````````````...----::/yhddssssyyyhyhyhhhdddddddmmdshdNMMNNNNNNNNNNNNNNNN\n" +
            "hhhhhhhhhhhhhhhhhhhhmMMMMMNmhyyyhhhddddddmmmmmmmmNNmyo:-.```````````````````````````....----:::::::oyhmyshyhyyyssyyhhhhhhdddddhshdNMMMNNNmNNNNNNNNNNNN\n" +
            "hhhhhhhhhhhhhhhhhhhhhmMMNmdyyyyyyhhhhhdddddddddddhhdmNNdy+:-.```````````````......-----::::::://///yhddyhddhyssssyyyyhhhhhhhdyyhdNNMmNNNmmNNNNNNNNNNNN\n" +
            "hhhhhhhhhhhhhhhhhhhdmMMNmhyyyyyhhhhhddddddddddddddshhmMNMNNmhs+:-.........----------::::::///////+ohhmmdmmdhssssyyhhhhhhdhhdyhddNmdddNNNNNNNNNNNNNNNNN\n" +
            "dddddddddddddddddddNMNNmmddyyyhhhhhdddddmmmmmmmmmdsshdNhhsydmNNNmhyo+/:------------:::::///////+++shdNNNMmdhssssyyhhhdddhhmddNmNdyyymNNNNNNNNNNNNNNNNN\n" +
            "dddddddddddddddddmMMNNNNmmhsyyyyyhhdddddddddmmmmmdssyhNyy///++syhdmNNNmdhso+/::---::::::///////+++yhmMNNNmdysoosyhhhhddhydmNMNNdyoyhNNNNNNNNNNNNNNNNNN\n" +
            "hhhhhhhhhhhhhhhhmMMMMNMNmhssssssyyyhhhhhhhdddddddhsshdds+:::::::///+ooshdmNNNNhs/::::::////////+++yhNMNNmmdhooosyyhhhhyohhNMMNdyoshdNNNNNNNNNNNNNNNmmN\n" +
            "hhhhhhhhhhhhhhhdMMMMNMMmdsssssssyyyhhhhhhhhhdddhdhshhmyo----::::::::::::/oydNMMNmy+/:::///+osyhdmmNMMNNNNNdhoooyyhhhhy+shmMMNdyooyhNNNNNNNNNNNNNNmdddd\n" +
            "dddddddddddddddmMMMmNMNddsssssssyyhhhhhhddddddymdhddmhs/------::::://+oyhmmNNmmNNNmyo///shmNNNmdhyyddhy+yhddsosyyhhddsohhNMNhs+oohdNNNNNNNNNNNNmdhhdmN\n" +
            "dddddddddddddddNMMmdMMNdhooooooosyhhhhhhdddddyhdmdNdds+---------:+oydNNNNmys+//+ydNNhs/+hdNhy/:---:osoo/syhhyosyhddddoyhmMmhs++oyhmNNNNNNNNNNNmhyydmMm\n" +
            "hhhhhhhhhhhhhhhmMmddMMNdyooooooosyyyhhhhhhhhysddNNNmyo----------+ydNmho//o+//:::/shmNds+shmys:----:://+ossyososyyhhhyoddMmho/+ooyhNNNNNNNNNNmdysshmNNm\n" +
            "hhhhhhhhhhhhhhhmmdddMMNdhoooooooosyyyyyyyyhy+shmMඞMhs/----------/shNhs/---++//::::+ydNhs+yhds+---:::/oso/+sssysyyhhhohhNmhs/+ooshdNNNNNNNNNmdsoshdNNmd\n" +
            "hhhhhhhhhhhhhhhhhhhhNMMmdsooooooosyyyyhhhhho+yhNMMNyo:-----------:oymmyo:--/+//::::/shmhs+ydys:-:::+o+////oyydyyyhhhydmNhs/++ooyhmNNNNNNNNmdhsshdNNmdh\n" +
            "hhhhhhhhhhhhhhhhhhhhdMMMmdsoooooosyyhhhhhhyo+yhmMNms+--------------:ohmds+--:++/////+hddy++yhso:::/+//::///yyddhhdddmmNhy//+ooshdNNNNNNNNNddyshdNNmddh\n" +
            "hhhhhhhhhhhhhhhhhhhhhdMMMmdyoooooosyyhhhhhoo/hdhmhss/:::-------------:oydhs/--/++/+oydNNmyoyhsy/:/:::::////+shmNddddmNdy+/+oooyhmNNNNNNNNmdyshdNNmddhh\n" +
            "hhhhhhhhyhhhhhhhhhhhhhhNMMNmdsooooossyyyyy++/hhyy/:::::::::::::::------:oydhs/:/sydmNNNmNNNmNdys:::::////////symNNdmmmho//+ooshhNNNNNNNNNdhsydmNmdhhhh\n" +
            "hhhhhhhhhhhhhhhhhhhhhhhhdNMMMNdysoooosyyyyo+ohdhs:::::::::::::::::::::::::oydddddNNhyssssyydmMdy+::://////////+shNMMNdy//+oooyhmNNNNNNNNmdsshmMmdhhhhh\n" +
            "hhhhhhhhhhhhhhhhhhhhhhhhhhdmNMMNNdysoosyyho+shmMdy+::::::::::::::::::::::::/oydNNNhssssssssyhmNys://////////////+shmdh+/+oooshdNNNNNNNNmdhydmMmddhhhdh\n" +
            "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhdmNMMMNdhsssysoyhmMMNdy+::::::::::::::::::::::::oydNmyssssssyyyhdNhy://///////////////+/+//+oooyhNNNNNNNNNddyhmMmdhhhhhhh\n" +
            "hhhhhhhhhyhyyhhhhhhhhhhhhhhhhhhhhdmNMMNNddhsddmdmMMNmy+/:::::::::::::::::::::/yhNNhyyyyyyyyhddNhs//////////////////////+oooshdMNNNNNNNmdyhdNmdhyhhhhhh\n" +
            "hhyyyyyyyyyyyyyhhhhhhhyhhhhhhhhhhhhhhddmNMMMMMmdhdNMMNmho/::::::::::::::::::::oydNNmhhyyyyhhdNmh+/////////////////////+ooooyhNNNNNNNNNdhhdNNdhyyhhyyhy\n" +
            "hhhhhyhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhdddhhhhhdNMMNmhs+/:::::::::::://++oshdNMMMNmmmmNNMMMNmhyso+++++++++++ooooooooooyhmNNNNNNNNmdddNNdhhyyhhyhhy\n" +
            "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhmMMMNmdddhhhhhhhddmmmNNMMMMMMMNNNNMMMMMNNNMMMMMMNmdhhysoooooooooooosshdNNNNNNNNmdmdmNddhhhhhhhhhh\n" +
            "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhmMMMNNmmmNNNNNNNNNNMMMNhhmmyshdmNmmMMNNNNNNNNNMMMMMMMMMNNNNmmmNNNMMMMNNNNNNNNdmmmNddhhhhhhhhhhh";

}
